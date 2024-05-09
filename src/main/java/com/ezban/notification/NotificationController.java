package com.ezban.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ezban.notification.model.Notification;
import com.ezban.notification.model.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notifyservice;

    @GetMapping
    public List<Notification> getAllNotifications() {
    	
        return notifyservice.getAllNotifications();
    }

    @GetMapping("/{notificationNo}")
    public Notification getNotificationById(@PathVariable Integer notificationNo) {
    	
        return notifyservice.getNotificationById(notificationNo);
    }
    
    @GetMapping("/mem-notification/{memberNo}")
    public List<Notification> findByMemberNo(@PathVariable Integer memberNo){
    	
    	return notifyservice.findByMemberNo(memberNo);
    }
    
    @GetMapping("/host-notification/{hostNo}")
    public List<Notification> findByHostNo(@PathVariable Integer hostNo){
    	
    	return notifyservice.findByHostNo(hostNo);
    }
    
    @GetMapping("/admin-notification/{adminNo}")
    public List<Notification> findByAdminNo(@PathVariable Integer adminNo){
    	
    	return notifyservice.findByAdminNo(adminNo);
    }
    
    public List<Notification> findAllByOrderByNotificationTimeDesc(){
    	
        return notifyservice.findAllByOrderByNotificationTimeDesc();
    }
    
    @GetMapping("/notification-asc")
    public List<Notification> findAllByOrderByNotificationTimeAsc(){
    	
    	return notifyservice.findAllByOrderByNotificationTimeAsc();
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
    	
        return notifyservice.saveNotification(notification);
    }

    @PutMapping("/{notificationNo}/markAsRead")
    public void markNotificationAsRead(@PathVariable Integer notificationNo) {
    	
    	notifyservice.markAsRead(notificationNo);
    }

}