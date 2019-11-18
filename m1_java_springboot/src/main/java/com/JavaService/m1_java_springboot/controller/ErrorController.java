package com.JavaService.m1_java_springboot.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/m1_java_springboot/error")
public class ErrorController {
	
	@CrossOrigin
	@RequestMapping(value="/unauthorize")
	public HashMap<String,Object> UnAuthorize(){
		HashMap<String,Object> result = new HashMap<String,Object>(){
			{
				put("message","UnAuthorize");
			}
		};
		
		try {
			
		}catch(Exception e) {
			result.put("error", e.getMessage());
		}
		return result;
	}
}
