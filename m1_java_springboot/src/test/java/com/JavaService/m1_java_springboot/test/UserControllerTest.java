package com.JavaService.m1_java_springboot.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.JavaService.m1_java_springboot.controller.*;
import com.JavaService.m1_java_springboot.dao.*;
import com.JavaService.m1_java_springboot.entity.*;
import com.JavaService.m1_java_springboot.model.*;
import com.JavaService.m1_java_springboot.repository.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
//@SpringBootTest(
//		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//		classes = UserController.class
//		)
public class UserControllerTest {

	   @Autowired
	   private MockMvc mvc;
	   
	   @Autowired
	   private UserRepository userRepo;
	   
	   @Autowired
	   private JdbcTemplate jdbcTemplate;
	   
	   @MockBean
	   private UserController userController;
	   
	   @Test
	   public void PostUser() throws Exception {
		   ObjectMapper ObjJackson = new ObjectMapper(); 
	       UserDao userDao = new UserDao();
	       UserModel userModel = new UserModel();
	       userModel.setUser_id(System.currentTimeMillis()+"");
	       userModel.setUser_name("testing 1");
	       userModel.setUser_email("test_1@gmail.com");
	       userModel.setUser_password("test1Password");
	       
	       UserEntity userEntity = new UserEntity();
//	       
//	       when(this.userRepo.Insert(ObjJackson.writeValueAsString(userModel)))
//	       .thenReturn(1);
//	       
//	       verify(this.userRepo.Insert(ObjJackson.writeValueAsString(userModel)));
	   }
}
