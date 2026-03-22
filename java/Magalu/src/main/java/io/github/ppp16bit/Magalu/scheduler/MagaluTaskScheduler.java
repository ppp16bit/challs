package io.github.ppp16bit.Magalu.scheduler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.github.ppp16bit.Magalu.service.NotificationService;

@Component
public class MagaluTaskScheduler {
    private static final Logger logger = LoggerFactory.getLogger(MagaluTaskScheduler.class);
    private final NotificationService notificationService;

    public MagaluTaskScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void checkTasks() {
        var dateTime = LocalDateTime.now();
        logger.info("running at {}", dateTime);
        notificationService.checkAndSend(dateTime);
    }
}