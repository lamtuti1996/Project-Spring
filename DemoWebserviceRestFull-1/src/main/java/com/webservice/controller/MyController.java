package com.webservice.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyController {
	
	
	@GetMapping("/getAll")
	public void getAll() {
		String uri="http://localhost:8082/client/v1/list";
		RestTemplate restTemplate =new RestTemplate();
		List<LinkedHashMap<String,Object>> usersMap=restTemplate.getForObject(uri, List.class);
		if(usersMap!=null){
	        for(LinkedHashMap<String, Object> map : usersMap){
	            System.out.println("User : username="+map.get("username")+", password="+map.get("password")+", fullname="+map.get("fullname"));
	        }
	    }else{
	        System.out.println("No user exist----------");
	    }
	}

}
