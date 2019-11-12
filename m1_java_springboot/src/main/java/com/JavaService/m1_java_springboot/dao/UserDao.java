package com.JavaService.m1_java_springboot.dao;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.JavaService.m1_java_springboot.entity.*;
import com.JavaService.m1_java_springboot.model.*;
import com.JavaService.m1_java_springboot.repository.*;
import com.JavaService.m1_java_springboot.service.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper; 



@Service
public class UserDao {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserService userService;
	
	
	@Transactional(rollbackFor = {Exception.class},propagation=Propagation.REQUIRED)
	public int InsertUser(UserModel dataUser){
		int process = 0;
		try {
			dataUser.setUser_password(this.userService.EncryptPassword(dataUser.getUser_password()));
			dataUser.setUser_id("USR-"+System.currentTimeMillis()+"");
			
			ObjectMapper ObjJackson = new ObjectMapper(); 
			
			UserEntity userEntity = new UserEntity();
			
			userEntity.setData(ObjJackson.writeValueAsString(dataUser));
			userEntity.setUser_name(dataUser.getUser_name());
			userEntity.setUser_password(dataUser.getUser_password());
			userEntity.setUser_id(dataUser.getUser_id());
			this.userRepository.save(userEntity);
			process = 1;
		}
		catch (IOException ex) {
			ex.printStackTrace();
			TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
			TransactionInterceptor.currentTransactionStatus().flush();
		}
		
		return process;
	}
}
