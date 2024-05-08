package com.ezban.notification.model;

import com.ezban.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notifyrepository;

    public List<Notification> getAllNotifications() {
        return notifyrepository.findAll();
    }

    public Notification getNotificationById(Integer notificationNo) {
        return notifyrepository.findById(notificationNo).orElse(null);
    }

    public Notification saveNotification(Notification notification) {
        return notifyrepository.save(notification);
    }
    
    public List<Notification> findByMemberNo(Integer memberNo){
    	
    	return notifyrepository.findByMemberNo(memberNo);
    }
    
    public List<Notification> findByHostNo(Integer hostNo){
    	
    	return notifyrepository.findByHostNo(hostNo);
    }
    
    public List<Notification> findByAdminNo(Integer adminNo){
    	
    	return notifyrepository.findByAdminNo(adminNo);
    }
    
    public List<Notification> findAllByOrderByNotificationTimeDesc(){
    	
    	return notifyrepository.findAllByOrderByNotificationTimeDesc();
    }
    
    public List<Notification> findAllByOrderByNotificationTimeAsc(){
    	
    	return notifyrepository.findAllByOrderByNotificationTimeAsc();
    }

    public void markAsRead(Integer notificationNo) {
        Notification notification = notifyrepository.findById(notificationNo).orElse(null);
        if (notification != null) {
            notification.setReadStatus((byte) 1);
            notifyrepository.save(notification);
        }
    }
}

