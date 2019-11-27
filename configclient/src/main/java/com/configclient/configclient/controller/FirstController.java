package com.configclient.configclient.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firstcontroller")
public class FirstController {
	
	@Value("${location_java_home}")
	public String locConfig;
	
	@Value("${app.name}")
	public String app_name;
	
	@RequestMapping("/config")
	public List<String> Config() {
		List<String>  res = new ArrayList<String>();
		
		res.add(this.locConfig);
		res.add(this.app_name);
		
		return res;
	}
}
