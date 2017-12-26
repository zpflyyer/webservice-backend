package com.hui.personal.resources;

import com.hui.personal.WebServiceConstants;
import com.hui.personal.model.Passenger;
import com.hui.personal.service.TripService;
import com.hui.personal.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController("/")
public class TripResource implements WebServiceConstants{
    @Autowired
    TripService tripService;


    @RequestMapping("/getTrips")
    public Map<String,List<Passenger>> getTickets(){
        return tripService.getTrips();
    }

    @RequestMapping("/order")
    public String order(@RequestParam String name, @RequestParam String phoneNum, @RequestParam String dateTime){

        return tripService.order(name,phoneNum, dateTime);
    }

    @RequestMapping("/cancel")
    public String cancel(@RequestParam String name, @RequestParam String phoneNum, @RequestParam String dateTime){

        return tripService.cancel(name,phoneNum, dateTime);
    }

}
