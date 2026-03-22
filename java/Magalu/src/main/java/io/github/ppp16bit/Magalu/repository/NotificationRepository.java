package io.github.ppp16bit.Magalu.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.ppp16bit.Magalu.entity.Notification;
import io.github.ppp16bit.Magalu.entity.Status;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatusInAndDateTimeBefore(
        List<Status> status,
        LocalDateTime dateTime
    );
}