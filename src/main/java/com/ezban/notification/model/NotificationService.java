package com.ezban.notification.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationHandler notificationHandler;
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationHandler notificationHandler, NotificationRepository notificationRepository) {
        this.notificationHandler = notificationHandler;
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(String message) {
        Notification notification = new Notification();
        notification.setNotificationContent(message);
        notification.setNotificationTime(Timestamp.valueOf(LocalDateTime.now()));
        notificationRepository.save(notification);
        notificationHandler.sendNotification(notification);
    }
}

