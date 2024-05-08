package com.ezban.notification.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
	List<Notification> findByMemberNo(Integer memberNo);
	
	List<Notification> findByHostNo(Integer hostNo);
	
	List<Notification> findByAdminNo(Integer adminNo);
	
	List<Notification> findAllByOrderByNotificationTimeDesc();
	
	List<Notification> findAllByOrderByNotificationTimeAsc();

}
