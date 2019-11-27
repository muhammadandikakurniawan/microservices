package com.JavaService.m1_java_springboot.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.JavaService.m1_java_springboot.entity.PostEntity;
import com.JavaService.m1_java_springboot.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HttpClientService {
	
	public List<Object> PostGetList(String url) throws ClientProtocolException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost(url);
	    httpPost.setHeader("Content-Type", "application/json");
	    httpPost.setHeader("Accept", "application/json");
	    CloseableHttpResponse response = client.execute(httpPost);
	    String content = EntityUtils.toString(response.getEntity());
	    List<Object> dt = Arrays.asList(objMapper.readValue(content, Object[].class));
	    client.close();
	    return dt;
	}
}
