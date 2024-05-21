package com.ezban.notification.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationHandler {

    private final List<SimpMessagingTemplate> messagingTemplates = new ArrayList<>();

    @Autowired
    public NotificationHandler(List<SimpMessagingTemplate> messagingTemplates) {
        this.messagingTemplates.addAll(messagingTemplates);
    }

    public void sendNotification(Notification notification) {
        for (SimpMessagingTemplate template : messagingTemplates) {
            template.convertAndSend("/topic/notifications", notification);
        }
    }
}