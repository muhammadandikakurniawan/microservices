package com.JavaService.m1_java_springboot.controller;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.JavaService.m1_java_springboot.dao.*;
import com.JavaService.m1_java_springboot.model.*;
import com.JavaService.m1_java_springboot.repository.*;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/m1_java_springboot/user")
public class UserController {
	@Autowired 
	private UserDao userDao;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@CrossOrigin
	public HashMap<String,String> PostUser(@Valid @RequestBody UserModel dataUser) throws Exception{
		HashMap<String,String> result = new HashMap<String,String>(){ {put("status","failed");} };
		try {
			Integer process = this.userDao.InsertUser(dataUser);
			result.replace("status", process >= 1 ? "success":"failed");
		}
		catch(Exception ex) {
			result.replace("status", "error");
			result.put("message", ex.getMessage());
		}
		dataUser.setUser_id(System.currentTimeMillis()+"");
		return result;
	}
}
