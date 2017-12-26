package com.hui.personal;

import com.hui.personal.persistence.TripManager;
import com.hui.personal.util.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Slf4j
public class Test implements WebServiceConstants{
    @SneakyThrows
    public static void main(String[] args) {
//        TripManager.instance.getTrips().values().stream().forEach(trip -> {
//            log.info(trip.toString());
//        });
        log.info(TripManager.instance.ordered("2017-12-27,13:00","12345678902").toString());
    }
}
