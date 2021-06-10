package com.udemy.SpringBootRS.Controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	@Autowired
	Greeting greeting;
	

	Random random = new Random();
	
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value ="name")String name) {
		
		long id = (long) (Math.random() * 40);
		greeting.setId(id);
		greeting.setContent("Hey I'm learning spring from " + name);
		return greeting;
		
	}

}
