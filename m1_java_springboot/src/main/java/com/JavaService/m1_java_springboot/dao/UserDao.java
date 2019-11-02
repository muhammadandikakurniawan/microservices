package com.JavaService.m1_java_springboot.dao;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.JavaService.m1_java_springboot.entity.UserEntity;
import com.JavaService.m1_java_springboot.model.*;
import com.JavaService.m1_java_springboot.repository.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper; 

@Service
public class UserDao {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Transactional(rollbackFor = {Exception.class},propagation=Propagation.REQUIRED)
	public int InsertUser(UserModel dataUser){
		int process = 0;
		try {
			ObjectMapper ObjJackson = new ObjectMapper(); 
			UserEntity userEntity = new UserEntity();
			userEntity.setData(ObjJackson.writeValueAsString(dataUser));
//			process =  this.userRepository.Insert(ObjJackson.writeValueAsString(userEntity));
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
