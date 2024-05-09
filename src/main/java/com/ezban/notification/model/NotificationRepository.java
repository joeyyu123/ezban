package com.ezban.notification.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
	List<Notification> findByMemberNo(Integer memberNo);
	
	List<Notification> findByHostHostNo(Integer hostNo);
	
	List<Notification> findByAdminAdminNo(Integer adminNo);
	
	List<Notification> findAllByOrderByNotificationTimeDesc();
	
	List<Notification> findAllByOrderByNotificationTimeAsc();

}
