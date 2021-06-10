package com.udemy.SpringBootRS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.udemy.SpringBootRS.repository.LibraryRepository;

@SpringBootApplication
public class SpringBootRsApplication{
	
	@Autowired
	LibraryRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRsApplication.class, args);
	}



}
