package com.JavaService.m1_java_springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.JavaService.m1_java_springboot.dao.*;
import com.JavaService.m1_java_springboot.entity.*;
import com.JavaService.m1_java_springboot.model.*;
import com.JavaService.m1_java_springboot.repository.*;
import com.JavaService.m1_java_springboot.service.HttpClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/m1_java_springboot/post")
public class PostController {
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private HttpClientService httpClientService;
	
	@Autowired
	private HttpServletResponse res;
	
	@Autowired
	private HttpServletRequest req;
	
	private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	@CrossOrigin
	@RequestMapping(value = "/testapi", method = RequestMethod.POST)
	public HashMap<String,String> testapi(@RequestBody String data){
		HashMap<String,String> result = new HashMap<String,String>(){ {put("status","failed");} };
		try {
			result.put("data", data);
			result.put("header", this.req.getHeader("Authorization"));
			result.put("content_type", req.getHeader("Content-Type"));
			System.out.println("=============================== IN CONTROLLER ====================================");
			System.out.println("auth = "+this.req.getHeader("Authorization"));
			System.out.println("data = "+data);
			System.out.println("=============================== IN CONTROLLER ====================================");
		}catch(Exception ex) {
			System.out.println("=============================== CONTROLLER ERROR ====================================");
		}
		return result;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public HashMap<String,String> insertPost(@RequestBody PostModel dataPost){
		HashMap<String,String> result = new HashMap<String,String>(){ {put("status","failed");} };
		System.out.print("get post by user id = "+this.userRepository.getUserById(dataPost.getUser_id()).size());
		try {
			System.out.println("=============================== IN CONTROLLER ====================================");
			System.out.println("auth = "+this.req.getHeader("Authorization"));
			if(this.userRepository.getUserById(dataPost.getUser_id()).size() != 0) {
				String statusInsert = this.postDao.insertPost(dataPost).get("status").toString().equalsIgnoreCase("success")? "success":"failed";
				result.replace("status", statusInsert);
//				SseEmitter sseEmitter = new SseEmitter();
			}
			else {
				result.replace("status", "failed");
				result.put("message","user id is not valid");
			}
		}catch(Exception ex) {
			result.replace("status", "error");
			result.put("message", ex.getMessage());
			System.out.println("=============================== CONTROLLER ERROR ====================================");
		}
		return result;
	}
	
	@CrossOrigin
	@PostMapping(value="/get",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<PostEntity> getPost(@RequestBody PostEntity data){
		HashMap<String,String> result = new HashMap<String,String>(){ {put("status","failed");} };
		System.out.println("=============================== CONTROLLER GET ====================================");
		System.out.println("auth = "+this.req.getHeader("Authorization"));
		return this.postRepository.findAll();
	}
	
	@CrossOrigin
	@PostMapping(value="/getcl",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<Object> getPostCl() throws ClientProtocolException, IOException{
		return this.httpClientService.PostGetList("http://localhost:8080/m1_java_springboot/post/get");
	}
	
	@CrossOrigin
	@PostMapping(value="/getbytitle",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<PostEntity> getPostByTitle(@RequestBody PostModel data) throws ClientProtocolException, IOException{
		ObjectMapper objMapper = new ObjectMapper();
		System.out.println("=============================== CONTROLLER getbytitle ====================================");
		System.out.println("data = "+objMapper.writeValueAsString(data));
		return this.postRepository.GetPostByTitle(data.getPost_title());
	}
	
	@CrossOrigin
	@PostMapping(value="/filter",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<Object> filtePost(@RequestBody PostEntity data) throws ClientProtocolException, IOException{
		ObjectMapper objMapper = new ObjectMapper();
		System.out.print("request param = ");
		System.out.println(objMapper.writeValueAsString(data));
		List<Object> result = new ArrayList<Object>();
 		try {
			result = this.postDao.filterPost(data);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return result ;
	}
	

}
