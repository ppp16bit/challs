package io.github.ppp16bit.Magalu.controller.dto;

import java.time.LocalDateTime;

import io.github.ppp16bit.Magalu.entity.ChannelValues;
import io.github.ppp16bit.Magalu.entity.Notification;
import io.github.ppp16bit.Magalu.entity.StatusValues;

public record SchedulerNotificationDTO(LocalDateTime dateTime, String destination, String message, ChannelValues channel) {
    public Notification toNotification() {
        return new Notification(
            dateTime,
            destination,
            message,
            channel.toChannel(),
            StatusValues.PENDING.toStatus()
        );
    }
}