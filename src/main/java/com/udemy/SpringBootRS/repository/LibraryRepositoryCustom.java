package com.udemy.SpringBootRS.repository;

import java.util.List;

import com.udemy.SpringBootRS.Controller.Library;


public interface LibraryRepositoryCustom {
	
	List<Library> findAllByAuthor(String author);

}
