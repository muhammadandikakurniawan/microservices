package com.JavaService.m1_java_springboot.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.JavaService.m1_java_springboot.dao.PostDao;
import com.JavaService.m1_java_springboot.entity.PostEntity;
import com.JavaService.m1_java_springboot.model.PostModel;
import com.JavaService.m1_java_springboot.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
//@MessageMapping("/m1_java_springboot/realtimepost")
@CrossOrigin
public class RealtimePostController {
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private UserRepository userRepo;
	
	private SimpMessagingTemplate messTemp;
	
	@Autowired 
	RealtimePostController(SimpMessagingTemplate tmp){
		this.messTemp = tmp;
	}
	
//	when access = /app/mess not just using mess, because has been set in spring boot websocket configuration
    @MessageMapping("/mess")
	public void getPost(String mess) {
    	ObjectMapper objMapper = new ObjectMapper();
    	System.out.println("=========================================== ws ===========================================");
    	System.out.println(mess);
    	System.out.println("=========================================== ws ===========================================");
    	
    	this.messTemp.convertAndSend("/topic",mess);
    }
    
    @MessageMapping("/realtime/post/insert")
    public void InsertPost(PostModel newPost) throws JsonProcessingException {
    	ObjectMapper objMapper = new ObjectMapper();
    	HashMap<String,Object> res = new HashMap<String,Object>();
    	PostEntity postEntity = new PostEntity();
    	postEntity.setUser_id(newPost.getUser_id());
    	postEntity.setPost_title(null);
    	postEntity.setPost_id(null);
    	
    	System.out.println("=========================================== Param ===========================================");
    	System.out.println(objMapper.writeValueAsString(newPost));
    	System.out.println("=========================================== --- ===========================================");
  
    	try {
    		if(this.userRepo.getUserById(newPost.getUser_id()).size() == 1){
    			try {
    				
    		    	System.out.println("=========================================== Process ===========================================");
    		    	System.out.println(this.postDao.insertPost(newPost));
    		    	System.out.println("=========================================== --- ===========================================");
    			}catch(Exception ex) {
    				res.put("message","error");
    		    	System.out.println("=========================================== Error ===========================================");
    		    	System.out.println(ex.getMessage());
    		    	System.out.println("=========================================== --- ===========================================");
    			}
    		}
    	}catch(Exception ex) {
    		res.put("message","error");
        	System.out.println("=========================================== Error ===========================================");
        	System.out.println(ex.getMessage());
        	System.out.println("=========================================== --- ===========================================");
    	}
    	res.put("countPost",this.postDao.filterPost(postEntity).size());
    	res.put("user_id", newPost.getUser_id());
    	
    	String result = objMapper.writeValueAsString(res);
    	
    	System.out.println("=========================================== Response ===========================================");
    	System.out.println(result);
    	System.out.println("=========================================== --- ===========================================");
    	
    	this.messTemp.convertAndSend("/post_topic",res);
    }
    
	
}
