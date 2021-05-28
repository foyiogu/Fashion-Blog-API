package com.webblog.ConfigurationManager;

import com.webblog.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;


@Component
public class SchedulerConfiguration {

    @Autowired
    PersonService personService;

    @Scheduled(fixedRate = 60000L)
    public void scheduleTaskWithFixedRate() {
        personService.deactivatedPersonScheduler();
    }
}
