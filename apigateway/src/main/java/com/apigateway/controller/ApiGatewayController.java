package com.apigateway.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

import com.apigateway.dao.ApiGatewayDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping(value="/apigateway")
public class ApiGatewayController {
	
	@Autowired
	private ApiGatewayDao apiGatewayDao;
	
	@Autowired
	private HttpServletRequest httpRequest;
	
	@Autowired
	private HttpServletResponse httpResponse;
	
	@ApiOperation(value = "Get arrival by ID")
	@RequestMapping(value="/post/filter")
	public HashMap<String,Object> PostFilter(@RequestBody String data) throws ClientProtocolException, IOException{
		HashMap<String,Object> result = new HashMap<String,Object>();
		ObjectMapper objMapper = new ObjectMapper();
		 CloseableHttpClient client = HttpClients.createDefault();
		 StringEntity entity = new StringEntity(data);
		 HttpPost httpPost = new HttpPost("http://localhost:8885/m1_java_springboot/post/filter");
		 httpPost.addHeader("Authorization",httpRequest.getHeader("Authorization"));
		 httpPost.addHeader("Content-Type","application/json");
		 httpPost.addHeader("Accept","application/json");
		 httpPost.setEntity(entity);
		 Integer addLogProc = this.apiGatewayDao.AddLog();
		 System.out.println("===========================================================================");
		 System.out.println("add log process = "+addLogProc);
		 System.out.println("data = "+data);
		 System.out.println("Authorization = "+httpRequest.getHeader("Authorization"));
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
	
	@RequestMapping(value="/test",method=RequestMethod.POST)
	public void TestApi(@RequestBody HashMap<String,Object> data) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		System.out.println("=====================================");
		System.out.println(data.get("data"));
		List<Object> dataList = (List<Object>) data.get("dataArr");
		((List<Object>) data.get("dataArr")).add(2000);
		String objArr = objMapper.writeValueAsString(dataList);
		System.out.println(objArr);
		System.out.println("=====================================");
		
	}
	
	@RequestMapping(value="/filemanage",method=RequestMethod.POST)
	public HashMap<String,Object> FileManageSave(@RequestParam("file") MultipartFile file) throws IOException {
		HashMap<String,Object> res = new HashMap<String,Object>();
		ObjectMapper objMapper = new ObjectMapper();
		
		CloseableHttpClient closeableHttp = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost("http://localhost:8885/m1_java_springboot/filemanage/base64upload");
		
		InputStream inputStrm = file.getInputStream();
		BufferedInputStream bufferFile = new BufferedInputStream(inputStrm);
		byte[] byteBuffer = ByteStreams.toByteArray(bufferFile);
		String base64File = Base64.encodeBase64String(byteBuffer);
		
		HashMap<String,Object> mapParams = new HashMap<String,Object>(){
			{
				put("file",base64File);
				put("data","from api gatewa");
			}
		};
		
		String Params = objMapper.writeValueAsString(mapParams);
		
//		ArrayList<NameValuePair> postParams = new ArrayList<NameValuePair>();
//		postParams.add(new BasicNameValuePair("file",base64File));
//		httpPost.setEntity(new UrlEncodedFormEntity(postParams));
		
		httpPost.setEntity(new StringEntity(Params));
		httpPost.addHeader("Authorization", this.httpRequest.getHeader("Authorization"));
		httpPost.addHeader("Content-Type","application/json");
		
		
		 CloseableHttpResponse response = closeableHttp.execute(httpPost);
		 String content = EntityUtils.toString(response.getEntity());
		 HashMap<String,Object> convContent = objMapper.readValue(content, HashMap.class);
		 response.close();
		 res.put("data", content);
		
		return res;
		
	}
}
