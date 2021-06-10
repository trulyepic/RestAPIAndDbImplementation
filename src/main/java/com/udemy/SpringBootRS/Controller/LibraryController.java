package com.udemy.SpringBootRS.Controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.HttpResource;

import com.udemy.SpringBootRS.repository.LibraryRepository;
import com.udemy.SpringBootRS.service.LibraryService;

@RestController
public class LibraryController {
	
	@Autowired
	LibraryRepository repository;
	
	@Autowired
	AddResponse res;
	
	@Autowired
	LibraryService libService;
	
	private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);
	

	@PostMapping("/addBook")
	public ResponseEntity<AddResponse> addBookImplementation(@RequestBody Library lib) {

		HttpHeaders headers = new HttpHeaders();
		String id = libService.buildId(lib.getIsbn(), lib.getAisle());
		
		if(!libService.checkBookAlreadyExist(id)) {
		headers.add("unique", id);
		lib.setId(id);
		res.setId(id);
		res.setMsg("Success Book is Added");
		repository.save(lib);
		
		logger.info("book doesn't exist, so book is created");
		return new ResponseEntity<>(res,headers,HttpStatus.CREATED);
		}
		
		else {
			
			res.setMsg("Book already exists");
			res.setId(id);
			
			logger.info("Book ");
			return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		}
				
	}
	
	@GetMapping("/getBooks/{id}")
	public Library getBookById(@PathVariable(value = "id")String id) {
		
		try {
		Library lib = repository.findById(id).get();
		return lib;
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/getAllAuthor")
	public List<Library> getAllBookByAuthor(@RequestParam("author")String authorName) {
		
		return repository.findAllByAuthor(authorName);
		
	}
	
	@PutMapping("/updateBook/{id}")
	public ResponseEntity<Library>updateBookById(@PathVariable("id")String id, 
												 @RequestBody Library libToupdate){
		
		Library updatedLib = libService.getBookById(id);
		
		updatedLib.setAisle(libToupdate.getAisle());
		updatedLib.setAuthor(libToupdate.getAuthor());
		updatedLib.setBook_name(libToupdate.getBook_name());
		repository.save(updatedLib);
		
		return new ResponseEntity<>(updatedLib,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBook")
	public ResponseEntity<String> deleteBookById(@RequestBody Library library) {
		
		String id = library.getId();
		Library bookTobeDeleted = libService.getBookById(id);
		repository.delete(bookTobeDeleted);
		logger.info("Book is deleted");
		
		return new ResponseEntity<>("Book is deleted",HttpStatus.CREATED);
		
	}
	
	@GetMapping("/getBooks")
	public List<Library> getAllBooks() {
		
		return repository.findAll();
		
	}
}
