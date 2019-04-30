package com.middleware.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collbackend.daos.NotificationDao;
import com.collbackend.models.Notification;

@RestController
public class NotificationController {

	@Autowired
	private NotificationDao notificationDao;

	@GetMapping(value="getAllNotification")
	public ResponseEntity<?> getAllNotification(HttpSession session){

		if(session.getAttribute("emailId")!=null){
			List<Notification> notificationList=notificationDao.getAllNotificationByEmail((String) session.getAttribute("emailId"));

			return new ResponseEntity<List<Notification>>(notificationList,HttpStatus.OK);
		}
		else {
			Error errorObj=new Error("Problem in fetching notification list");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(value="updateNotification")
	public ResponseEntity<?> updateNotification(@RequestParam int notificationId){

		System.out.println("updating notification");
		Notification notification=notificationDao.getNotificationById(notificationId);
		notification.setStatus("Viewed");

		if(notificationDao.addOrUpdateNotification(notification)) {

			return new ResponseEntity<String>("Successful",HttpStatus.OK);
		}

		else {
			System.out.println("Could not update");
			Error errorObj=new Error("Cannot be updated");
			return new ResponseEntity<Error>(errorObj,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
