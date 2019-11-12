package com.JavaService.m1_java_springboot.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.JavaService.m1_java_springboot.dto.ResultDto;
import com.JavaService.m1_java_springboot.entity.PostEntity;
import com.JavaService.m1_java_springboot.model.PostModel;
import com.JavaService.m1_java_springboot.repository.PostRepository;
import com.JavaService.m1_java_springboot.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;

@Service
public class PostDao {
 
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DataSource dataSource;
    
    private ResultDto resultDto = new ResultDto();
	
	@Transactional(rollbackFor = {Exception.class},propagation=Propagation.REQUIRED)
	public HashMap<String, Object> insertPost(PostModel dataPost){
		HashMap<String,Object> result = new HashMap<String,Object>();
		int process = 0;
		try{
			dataPost.setPost_id("POST-"+System.currentTimeMillis());
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			PostEntity postEntity = new PostEntity();
			
			postEntity.setPost_id(dataPost.getPost_id());
			postEntity.setUser_id(dataPost.getUser_id());
			postEntity.setPost_title(dataPost.getPost_title());
			postEntity.setPost_body(dataPost.getPost_body());
			postEntity.setPost_created_at(new Timestamp(new Date().getTime()).getTime());
			postEntity.setData(objectMapper.writeValueAsString(dataPost));
			
			this.postRepository.save(postEntity);
			process = 1;
			
			result.put("status", "success");
			
		}catch(Exception ex) {
			process = 0;
			result.put("status", "error");
			result.put("error message", ex.getMessage());
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			TransactionInterceptor.currentTransactionStatus().flush();
		}
		return result;
	}
	
	@Transactional(rollbackFor = {Exception.class},propagation=Propagation.REQUIRED)
	public List<Object> filterPost(PostEntity dataPost){
		HashMap<String,Object> result = new HashMap<String,Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		return this.jdbcTemplate.query("select * from filterPost(?,?,?)",
				new Object[]{
						dataPost.getPost_id(),
						dataPost.getUser_id(),
						dataPost.getPost_title()
				}, 
				(res,rown) -> new HashMap<String,Object>(){
					{
						put("id",res.getObject("id"));
						put("post_title",res.getObject("post_title"));
						put("post_id",res.getObject("post_id"));
						put("user_id",res.getObject("user_id"));
					}
				});
	}
	
	
}
