package com.middleware.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.collbackend.models.Chat;

@Controller
public class SockController {

	private final SimpMessagingTemplate simpMessagingTemplate;

	private List<String> users=new ArrayList<String>();

	@Autowired
	public SockController(SimpMessagingTemplate simpMessagingTemplate) {

		super();
		this.simpMessagingTemplate=simpMessagingTemplate;

	}

	@SubscribeMapping("/join/{username}")
	public List<String> join(@DestinationVariable String username){

		System.out.println("New User is "+username);
		if(!users.contains(username)) {

			users.add(username);
		}
		System.out.println("HEllo");
		simpMessagingTemplate.convertAndSend("/topic/join",username);
		return users;


	}

	@MessageMapping(value="/chat")
	public void chatRecieved(Chat chat) {

		//for group chat
		if(chat.getTo().equals("all")) {

			simpMessagingTemplate.convertAndSend("/queue/chats", chat);
		}
		else { //for private chat

			simpMessagingTemplate.convertAndSend("/queue/chats/"+chat.getTo(), chat);
			simpMessagingTemplate.convertAndSend("/queue/chats"+chat.getFrom(),chat);
		}

	}
}
