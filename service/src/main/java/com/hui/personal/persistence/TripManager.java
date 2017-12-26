package com.hui.personal.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hui.personal.WebServiceConstants;
import com.hui.personal.model.Passenger;
import com.hui.personal.util.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TripManager implements WebServiceConstants{
    //most efficient and clear way to create singleton instance: make date access entrance singleton(reference: JsonFactory.instance)
    public static final TripManager instance = new TripManager();
    @Getter
    private Map<String,List<Passenger>> trips = new ConcurrentHashMap<>();
    private String snapShot;
    private TripManager(){
        readTrips();
        writeTrips();
    }
    //flush date to new created file
    synchronized private void writeTrips(){
        log.info("writing trips file...");
        snapShot = DateUtil.getSnapShot().substring(0, snapFormat.length()-2).concat("00");
        File file = new File(pvParentPath + File.separator + snapShot + suffix);
        try {
            if (file.exists()){file.delete();}
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(file,trips);
        } catch (IOException e){
            log.error("meet error when create ticket file, snapShot: \"{}\"",snapShot);
        }
    }
    //keep orderes which are not out of date
    synchronized private void readTrips(){
        Date now = new Date();
        log.info("reading trips file...");
        String latestFilePath = DateUtil.getLatestFileIfExists();
        if (null != latestFilePath) {
            File latestFile = new File(latestFilePath);
            try {
                if (latestFile.exists()) {
                    //read tickets with valid set-off date
                    trips = new ObjectMapper().readValue(latestFile, new TypeReference<ConcurrentHashMap<String,List<Passenger>>>() {
                    });
                    SimpleDateFormat format = new SimpleDateFormat(standardFormat);
                    Set<String> tobeRemoved = new HashSet<>();
                    for (String setOff:
                         trips.keySet()) {
                        try {
                            if (format.parse(setOff).before(now)) {
                                tobeRemoved.add(setOff);
                            }
                        } catch (ParseException e){
                            log.warn("remove trip record with bad date format: \"{}\"", setOff);
                            tobeRemoved.add(setOff);
                        }
                    }
                    trips.keySet().removeAll(tobeRemoved);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            } finally {
                log.info("read records number: \"{}\"", trips.size());
            }
        }
    }

    //performance will keep reducing with more generation of new data, need fix this grave defficiency
    public void flush(){
        writeTrips();
    }

    public boolean hasNext(String setOff){
        return !trips.containsKey(setOff) || trips.get(setOff).size() < seatsPerTrip;
    }

    public Boolean ordered(String setOff, String phoneNum){
        //quick fail for performance
        if (! trips.containsKey(setOff)){
            return false;
        }
        for (Passenger p:
             trips.get(setOff)) {
            if (p.getPhoneNum().equalsIgnoreCase(phoneNum)) {
                return true;
            }
        }
        for (List<Passenger> passengers:
             trips.values()) {
            for (Passenger p:
                 passengers) {
                log.info(passengers.toString());
            }
        }
        return false;
    }
}
