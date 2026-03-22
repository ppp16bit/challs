package io.github.ppp16bit.Magalu.service;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import io.github.ppp16bit.Magalu.controller.dto.SchedulerNotificationDTO;
import io.github.ppp16bit.Magalu.entity.Notification;
import io.github.ppp16bit.Magalu.entity.StatusValues;
import io.github.ppp16bit.Magalu.repository.NotificationRepository;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void scheduleNotification(SchedulerNotificationDTO dto) {
        notificationRepository.save(Objects.requireNonNull(dto.toNotification()));
    }

    public Optional<Notification> findByid(Long notificationID) {
        return notificationRepository.findById(Objects.requireNonNull(notificationID));
    }

    public void cancelNotification(Long notificationID) {
        var notification = findByid(notificationID);
        if (notification.isPresent()) {
            notification.get().setStatus(StatusValues.CANCELED.toStatus());
            notificationRepository.save(Objects.requireNonNull(notification.get()));
        }
    }

    public void checkAndSend(LocalDateTime dateTime) {
        var notifications = notificationRepository.findByStatusInAndDateTimeBefore(
            List.of(StatusValues.PENDING.toStatus(), StatusValues.ERROR.toStatus()),
            dateTime
        );
        notifications.forEach(sendNotification());
    }

    private Consumer<Notification> sendNotification() {
        return n -> {
            n.setStatus(StatusValues.SUCESS.toStatus());
            notificationRepository.save(n);
        };
    }
}