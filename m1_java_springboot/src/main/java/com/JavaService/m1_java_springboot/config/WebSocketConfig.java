package com.JavaService.m1_java_springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic","/post_topic");
		config.setApplicationDestinationPrefixes("/app");
	}
	
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompReg) {
//		stompReg.addEndpoint("/m1_java_springboot_ws").setAllowedOrigins("*").withSockJS();
		stompReg.addEndpoint("/m1_java_springboot_ws").setAllowedOrigins("*");
	}
}
