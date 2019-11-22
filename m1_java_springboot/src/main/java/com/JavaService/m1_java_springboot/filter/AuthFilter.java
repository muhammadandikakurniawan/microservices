package com.JavaService.m1_java_springboot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component

public class AuthFilter implements Filter{
	
	@Autowired
	HttpServletRequest req;
	@Override
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		String Authorization = httprequest.getHeader("Authorization");
		System.out.println("===============================run===================================");
		System.out.println(httprequest.getParameter("file"));
		System.out.println("authorization (auth filter) : "+Authorization);
		System.out.println("endpoint : "+httprequest.getRequestURI());

		try {
			if(Authorization != null || Authorization != "") {
				if(Authorization.equalsIgnoreCase("basic 123")) {
					chain.doFilter(request, response);
				}
				else {
					httpresponse.sendError(httpresponse.SC_UNAUTHORIZED);
					System.out.println("Authorization is not valid");
				}
				System.out.println("auth is empty : "+( Authorization == ""));
				System.out.println("auth = basic 123 : "+( Authorization.equalsIgnoreCase("basic 123")));
			}
			else {
				System.out.println("UnAuthorized");
				httpresponse.sendError(httpresponse.SC_UNAUTHORIZED);
			}
		}
		catch(NullPointerException ex) {
			httpresponse.sendError(httpresponse.SC_UNAUTHORIZED);
			System.out.println("====================== error ======================");
			System.out.println(ex.getMessage());
			System.out.println("====================== error ======================");
		}
		
	}
}
