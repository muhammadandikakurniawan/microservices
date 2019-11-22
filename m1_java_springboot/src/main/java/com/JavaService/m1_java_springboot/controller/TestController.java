package com.JavaService.m1_java_springboot.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private Environment env;
	
	@Value("${service.name}")
	String serviceName;
	
	@RequestMapping(value="/envvar",method=RequestMethod.GET)
	public HashMap<String,Object> EnviVar(){
		HashMap<String,Object> res = new HashMap<String,Object>();
		
//		String env_name = this.env.getProperty("svr.name");
		String env_wind = System.getenv("service.name");
		
//		res.put("env_name",env_name);
		res.put("env_wind",this.serviceName);
		
		return res;
	}
	
}
