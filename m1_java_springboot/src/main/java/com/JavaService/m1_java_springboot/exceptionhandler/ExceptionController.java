package com.JavaService.m1_java_springboot.exceptionhandler;

import java.io.IOException;
import java.util.HashMap;

import javax.management.InvalidApplicationException;

import org.apache.http.auth.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


@RestControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(MissingServletRequestPartException.class)
	public HashMap<String,Object> EcxHandler(MissingServletRequestPartException exc){
		HashMap<String,Object> res = new HashMap<String,Object>(){
			{
				put("message","bad request coy");
			}
		};
		
		return res;
	}
	
	@ExceptionHandler(IOException.class)
	public HashMap<String,Object> EcxHandler(IOException exc){
		HashMap<String,Object> res = new HashMap<String,Object>(){
			{
				put("message","IO EXCP");
			}
		};
		
		return res;
	}
	
	@ExceptionHandler(MultipartException.class)
	public HashMap<String,Object> EcxHandler(MultipartException exc){
		HashMap<String,Object> res = new HashMap<String,Object>(){
			{
				put("message","MULTIPART EXCP");
			}
		};
		
		return res;
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public HashMap<String,Object> EcxHandler(HttpMediaTypeNotSupportedException exc){
		HashMap<String,Object> res = new HashMap<String,Object>(){
			{
				put("message","media type is not support");
			}
		};
		
		return res;
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public HashMap<String,Object> EcxHandler(AuthenticationException exc){
		HashMap<String,Object> res = new HashMap<String,Object>(){
			{
				put("message","you not deserve to access our services, hahahahaha");
			}
		};
		
		return res;
	}
}
