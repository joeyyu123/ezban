package com.ezban.notification.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
}
