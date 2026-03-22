package io.github.ppp16bit.Magalu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ppp16bit.Magalu.entity.Notification;
import io.github.ppp16bit.Magalu.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> getNotification(@PathVariable("notificationID") Long notificationID) {
        var notification = notificationService.findByid(notificationID);
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification.get());
    }

    @DeleteMapping("/{notificationID}")
    public ResponseEntity<Void> cancelNotification(@PathVariable("notificationID") Long notificationID) {
        notificationService.cancelNotification(notificationID);
        return ResponseEntity.noContent().build();
    }
}