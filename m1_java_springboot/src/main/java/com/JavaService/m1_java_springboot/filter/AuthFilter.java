package com.JavaService.m1_java_springboot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import javax.servlet.ServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
@Order(1)
public class AuthFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		try {
			if(!httprequest.getHeader("Authorization").equalsIgnoreCase("basic 123")) {
				throw new Exception();
			}
			else {
				chain.doFilter(request, response);
			}
		}
		catch(Exception e) {
			httpresponse.setStatus(401);
			httpresponse.setContentType("application/json");
		}
	}
}
