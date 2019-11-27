package com.configclient.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ConfigclientApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ConfigclientApplication.class, args);
	}

}
