package com.JavaService.m1_java_springboot.controller;

import java.io.IOException;
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

@RestController
@CrossOrigin
//@CrossOrigin(origins= {"*"})
//@CrossOrigin
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

	@RequestMapping(value = "/testapi", method = RequestMethod.POST)
	public HashMap<String,String> testapi(@RequestParam(value="data") String data){
		HashMap<String,String> result = new HashMap<String,String>(){ {put("status","failed");} };
//	       CorsConfiguration configuration = new CorsConfiguration();
//	        configuration.setAllowedOrigins(Arrays.asList("*"));
//	        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//		res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With, Accept");
		result.put("data", data);
		result.put("header", this.req.getHeader("Authorization"));
		result.put("content_type", req.getHeader("Content-Type"));
		return result;
	}
	

	@PostMapping(value = "/insert",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public HashMap<String,String> insertPost(@RequestBody PostModel dataPost){
		HashMap<String,String> result = new HashMap<String,String>(){ {put("status","failed");} };
		try {

			if(this.userRepository.getUserById(dataPost.getUser_id()) != null) {
				String statusInsert = this.postDao.insertPost(dataPost).get("status").toString().equalsIgnoreCase("success")? "success":"failed";
				result.replace("status", statusInsert);
			}
			else {
				result.replace("status", "failed");
				result.put("message","user id is not valid");
			}
		}catch(Exception ex) {
			result.replace("status", "error");
			result.put("message", ex.getMessage());
		}
		return result;
	}
	
	@PostMapping(value="/get",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<PostEntity> getPost(){
		HashMap<String,String> result = new HashMap<String,String>(){ {put("status","failed");} };
		return this.postRepository.findAll();
	}
	
	@PostMapping(value="/getcl",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<Object> getPostCl() throws ClientProtocolException, IOException{
		return this.httpClientService.PostGetList("http://localhost:8080/m1_java_springboot/post/get");
	}
	
	@PostMapping(value="/getbytitle",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	
	public List<PostEntity> getPostByTitle(@RequestBody PostModel data) throws ClientProtocolException, IOException{
		return this.postRepository.GetPostByTitle(data.getPost_title());
	}
	
	@PostMapping(value="/filter",produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	
	public List<Object> filtePost(@RequestBody PostEntity data) throws ClientProtocolException, IOException{
		return this.postDao.filterPost(data);
	}
	

}
