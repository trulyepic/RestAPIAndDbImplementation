package com.udemy.SpringBootRS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.SpringBootRS.Controller.Library;
import com.udemy.SpringBootRS.repository.LibraryRepository;

@Service
public class LibraryService {
	
	@Autowired
	private LibraryRepository repo;
	
	public String buildId(String isbn, int aisle) {
		
		String id = isbn+aisle;
		
		if (isbn.startsWith("Z")) {
			
			return "OLD"+id;
		}
		
		return id;
	}
	
	public boolean checkBookAlreadyExist(String id) {
		
		Optional<Library> lib = repo.findById(id);
		
		if(lib.isPresent()) {
			return true;
		}else
			
			return false;
		
	}
	
	public Library getBookById(String id) {
		
		return repo.findById(id).get();
	}

}
