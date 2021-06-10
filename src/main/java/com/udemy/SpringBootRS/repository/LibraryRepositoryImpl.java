package com.udemy.SpringBootRS.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import com.udemy.SpringBootRS.Controller.Library;


public class LibraryRepositoryImpl implements LibraryRepositoryCustom{
	
	@Autowired
	private LibraryRepository libraryRepository;

	@Override
	public List<Library> findAllByAuthor(String author) {
		
		List<Library> allBook = libraryRepository.findAll();
		//List<Library> booksByAuthor = new ArrayList<>();
		
//		for(Library i: allBook) {
//			if(i.getAuthor().equalsIgnoreCase(author)) {
//				
//				booksByAuthor.add(i);
//			}
//		} return booksByAuthor;
		
		//using Streams
		List<Library> bookByAuthor = allBook.stream()
				.filter(v -> v.getAuthor().equalsIgnoreCase(author))
				.collect(Collectors.toList());
		
		return bookByAuthor;
	} 
	
	

}
