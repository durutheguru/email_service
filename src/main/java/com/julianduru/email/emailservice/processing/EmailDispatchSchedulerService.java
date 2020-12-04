package com.julianduru.email.emailservice.processing;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * created by julian
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailDispatchSchedulerService {


    private final EmailRequestService emailRequestService;


    @Scheduled(cron = "${email.config.dispatch-cron:0 0/5 * 1/1 * ?}")
    @SchedulerLock(lockAtLeastFor = "1m", lockAtMostFor = "10m")
    public void scheduleEmailDispatch() {
        log.info("Running Email Dispatch Scheduler");
        emailRequestService.processEmailsPendingDispatch();
    }


}
