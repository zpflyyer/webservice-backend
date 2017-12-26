package com.hui.personal.service;

import com.hui.personal.WebServiceConstants;
import com.hui.personal.model.Passenger;
import com.hui.personal.persistence.TripManager;
import com.hui.personal.util.MessageUtil;
import com.hui.personal.util.ValidationUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@Service
public class TripService implements WebServiceConstants{
    @Autowired
    TripManager tripManager;

    public Map<String,List<Passenger>> getTrips(){
        return tripManager.getTrips();
    }

    public String order(String name,String phoneNum, String setOff){
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(new SimpleDateFormat(standardFormat).parse(setOff));
        } catch (ParseException e){
            log.error(setOff);
            return MessageUtil.getResponse(badDateFormat);
        }

        int checkResult = ValidationUtil.checkOrderInfo(name,phoneNum,date);
        if ( checkResult!= success ) {
            return MessageUtil.getResponse(checkResult);
        }
        if ( !tripManager.hasNext(setOff)){
            return MessageUtil.getResponse(saleOut);
        }
        if (tripManager.ordered(setOff,phoneNum)){
            return MessageUtil.getResponse(duplicated);
        }
        if (tripManager.getTrips().get(setOff) == null){
            tripManager.getTrips().put(setOff,new ArrayList<>());
        }
        tripManager.getTrips().get(setOff).add(new Passenger(name,phoneNum));
        return MessageUtil.getResponse(success);
    }
}
