package com.collbackend.daos;

import java.util.List;

import com.collbackend.models.Notification;

public interface NotificationDao {

	boolean addOrUpdateNotification(Notification notification);
	Notification getNotificationById(int notificationId);
	List<Notification> getAllNotificationByEmail(String email);
	boolean updateNotification(Notification notification);

}
