package com.middleware.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages="com")
public class WebScoketConfiguration implements WebSocketMessageBrokerConfigurer
{
	
	public void configureMessageBroker(MessageBrokerRegistry registry) {

		// /queue/ - destination prefix to send chat msg from server to client[chat]
		// /topic/ - destination prefix to send user name to all clients [String]
		registry.enableSimpleBroker("/queue/","/topic");
		registry.setApplicationDestinationPrefixes("/app");

	}


	public void registerStompEndpoints(StompEndpointRegistry registry) {

		registry.addEndpoint("/chatmodule").withSockJS();

	}


	
	  public void configureClientInboundChannel(ChannelRegistration registration) {
		
		//  WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
		 
	  
	  }
	  
	  
	  public void configureClientOutboundChannel(ChannelRegistration registration)
	  { 
		 // WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
			 
	  }

	
}
