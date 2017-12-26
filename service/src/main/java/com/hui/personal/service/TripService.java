package com.hui.personal.service;

import com.hui.personal.WebServiceConstants;
import com.hui.personal.model.Passenger;
import com.hui.personal.persistence.TripManager;
import com.hui.personal.util.MessageUtil;
import com.hui.personal.util.ValidationUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
            return MessageUtil.getOrderResponse(badDateFormat);
        }

        int checkResult = ValidationUtil.checkOrderInfo(name,phoneNum,date);
        if ( checkResult!= success ) {
            return MessageUtil.getOrderResponse(checkResult);
        }
        if ( !tripManager.hasNext(setOff)){
            return MessageUtil.getOrderResponse(saleOut);
        }
        if (tripManager.getRecords(setOff,phoneNum) != null){
            return MessageUtil.getOrderResponse(duplicated);
        }
        if (tripManager.getTrips().get(setOff) == null){
            tripManager.getTrips().put(setOff,new ArrayList<>());
        }
        tripManager.getTrips().get(setOff).add(new Passenger(name,phoneNum));
        return MessageUtil.getOrderResponse(success);
    }

    public String cancel(String name,String phoneNum, String setOff){
        Calendar date = Calendar.getInstance();
        try {
            date.setTime(new SimpleDateFormat(standardFormat).parse(setOff));
        } catch (ParseException e){
            log.error(setOff);
            return MessageUtil.getCancelResponse(badDateFormat);
        }

        int checkResult = ValidationUtil.checkOrderInfo(name,phoneNum,date);
        if ( checkResult!= success ) {
            return MessageUtil.getCancelResponse(checkResult);
        }
        Passenger p = tripManager.getRecords(setOff,phoneNum);
        if ( p == null){
            return MessageUtil.getCancelResponse(notFound);
        }
        tripManager.getTrips().get(setOff).remove(p);
        return MessageUtil.getCancelResponse(successCancel);
    }
}
