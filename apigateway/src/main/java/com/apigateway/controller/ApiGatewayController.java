package com.apigateway.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

import com.apigateway.dao.ApiGatewayDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/apigateway")
public class ApiGatewayController {
	
	@Autowired
	private ApiGatewayDao apiGatewayDao;
	
	@Autowired
	private HttpServletRequest httpRequest;
	
	@Autowired
	private HttpServletResponse httpResponse;
	
	@RequestMapping(value="/post/filter")
	public HashMap<String,Object> PostFilter(@RequestBody String data) throws ClientProtocolException, IOException{
		HashMap<String,Object> result = new HashMap<String,Object>();
		ObjectMapper objMapper = new ObjectMapper();
		 CloseableHttpClient client = HttpClients.createDefault();
		 StringEntity entity = new StringEntity(data);
		 HttpPost httpPost = new HttpPost("http://localhost:8888/m1_java_springboot/post/getbytitle");
		 httpPost.addHeader("Authorization",httpRequest.getHeader("Authorization"));
		 httpPost.addHeader("Content-Type","application/json");
		 httpPost.addHeader("Accept","application/json");
		 httpPost.setEntity(entity);
		 Integer addLogProc = this.apiGatewayDao.AddLog();
		 System.out.println("===========================================================================");
		 System.out.println("add log process = "+addLogProc);
		 System.out.println("data = "+data);
		 CloseableHttpResponse response = client.execute(httpPost);
		try {
			
			String content = EntityUtils.toString(response.getEntity());
			List<Object> getResponseJson = Arrays.asList(objMapper.readValue(content, Object[].class));
			result.put("data", getResponseJson);
		}catch(Exception e) {
			
			result.put("error_massage", e.getMessage());
		}
		this.httpResponse.setStatus(response.getStatusLine().getStatusCode());
		System.out.println("status code = "+response.getStatusLine().getStatusCode());
		
		return result;
	}
}
