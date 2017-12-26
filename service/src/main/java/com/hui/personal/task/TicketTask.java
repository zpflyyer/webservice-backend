package com.hui.personal.task;

import com.hui.personal.WebServiceConstants;
import com.hui.personal.persistence.TripManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TicketTask implements WebServiceConstants{
    @Autowired
    private TripManager tripManager;

    @Scheduled(fixedRate = 10000)
    public void updateTickets(){
        //sync in memory data with persistent store
        tripManager.flush();
    }
}
