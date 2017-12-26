package com.hui.personal.task;

import com.hui.personal.WebServiceConstants;
import com.hui.personal.persistence.TripManager;
import com.hui.personal.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Configuration
public class TicketInit implements WebServiceConstants{
    @Autowired
    private TripService tripService;

    @Bean
    @EventListener({ContextStartedEvent.class})
    public TripManager initTicket() {
        log.info("initializing ticket file");
        return TripManager.instance;
    }
}
