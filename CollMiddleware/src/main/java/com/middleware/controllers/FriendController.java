package com.middleware.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.collbackend.daos.FriendDao;
import com.collbackend.daos.NotificationDao;
import com.collbackend.daos.UserDao;
import com.collbackend.models.Friend;
import com.collbackend.models.Notification;
import com.collbackend.models.User;

@RestController
public class FriendController {

	@Autowired
	private FriendDao friendDao;
	@Autowired
	private UserDao userDao;

	@GetMapping(value="suggestedUsers")
	public ResponseEntity<?> getAllSuggestedUsers(HttpSession session){

		String email=(String) session.getAttribute("emailId");
		if(email==null) 
		{
			Error error=new Error("... please login to see suggested users.....");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<User> suggestedUsers=friendDao.getAllSuggestedUsers(email);
		return new ResponseEntity<List<User>>(suggestedUsers,HttpStatus.OK);


	}

	@Autowired
	NotificationDao notificationDao;

	@PostMapping(value="addFriend")
	public ResponseEntity<?> addFriend(HttpSession session,@RequestBody User toId){



		String email=(String)session.getAttribute("emailId");
		if(email==null) {
			Error error=new Error("...........please login to add.........");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		User fromId=userDao.getUserByEmail(email);
		Friend friend=new Friend();
		friend.setFromId(fromId);
		friend.setToId(toId);
		friend.setStatus('P');
		friendDao.addFriend(friend);

		//adding notification for friend request
		Notification notification=new Notification();
		notification.setDate(new Date());
		notification.setFromUser(fromId);
		notification.setToUser(toId);
		notification.setStatus("Not Viewed");
		notificationDao.addOrUpdateNotification(notification);
		return new ResponseEntity<Void>(HttpStatus.OK);	

	}

	@GetMapping(value="pendingRequests")
	public ResponseEntity<?> pendingRequests(HttpSession session){
		String email=(String)session.getAttribute("emailId");
		if(email==null) {
			Error error=new Error(".... please login.....");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<Friend> pendingRequests=friendDao.getPendingRequests(email);
		System.out.println("pending request "+pendingRequests);
		return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);
	}

	@PutMapping(value="acceptRequest")
	public ResponseEntity<?> acceptFriendRequest(@RequestBody Friend pendingRequest, HttpSession session){

		String email=(String)session.getAttribute("emailId");
		if(email==null)
		{
			Error error=new Error(".......please login to accept request.....");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		pendingRequest.setStatus('A');
		friendDao.acceptRequest(pendingRequest);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping(value="/deleteRequest")
	public ResponseEntity<?> deleteFriendRequest(@RequestBody Friend pendingRequest,HttpSession session){

		String email=(String)session.getAttribute("emailId");
		if(email==null) {
			Error error=new Error("....please login to delete request.....");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		friendDao.deleteRequest(pendingRequest);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping(value="listOfFriends")
	public ResponseEntity<?> listOfFriends(HttpSession session){
		String email=(String)session.getAttribute("emailId");
		if(email==null) {
			Error error=new Error("Login to get list of friends");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<Friend> friends=friendDao.listOfFriends(email);
		return new ResponseEntity<List<Friend>>(friends,HttpStatus.OK);
	}
}
