package com.udemy.SpringBootRS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.SpringBootRS.Controller.AddResponse;
import com.udemy.SpringBootRS.Controller.Library;
import com.udemy.SpringBootRS.Controller.LibraryController;
import com.udemy.SpringBootRS.repository.LibraryRepository;
import com.udemy.SpringBootRS.service.LibraryService;

import jdk.net.SocketFlow.Status;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc

class SpringBootRsApplicationTests {
	
	@Autowired
	LibraryController con;
	
	@MockBean
	LibraryService libService;
	
	@MockBean
	LibraryRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void checkBuildIdLogic() {
		
		LibraryService libraryService = new LibraryService();
		String actual = libraryService.buildId("ZIRT", 57);
		String expected = "OLDZIRT57";
		assertEquals(actual, expected);
	}
	
	@Test
	public void addBookTest() {
		
		Library lib = buildLibrary();
		when(libService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
		when(libService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(lib);
		
		ResponseEntity<AddResponse> response = con.addBookImplementation(lib);
		HttpStatus actual = response.getStatusCode();
		HttpStatus expected = HttpStatus.CREATED;
		
		assertEquals(actual, expected);
		AddResponse ad = response.getBody();
		ad.getId();
		assertEquals(ad.getId(), lib.getId());
		ad.getMsg();
		assertEquals("Success Book is Added", ad.getMsg());
	
	}
	
	@Test
	public void addBookControllerTest() throws Exception {
		
		Library lib = buildLibrary();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(lib);
		
		
		when(libService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
		when(libService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(lib);
		
		this.mockMvc.perform(post("/addBook")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(lib.getId()));
	}
	
	@Test
	public void getBookByAuthorTest() throws Exception{
		
		Library lib = buildLibrary();
		List<Library> li = new ArrayList<>();
		li.add(lib);
		li.add(lib);
		
		when(repository.findAllByAuthor(any())).thenReturn(li);
		
		this.mockMvc.perform(get("/getAllAuthor")
				.param("author", "Shety"))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	
	@Test
	public void updateBookTest() throws Exception {
		
		Library lib = buildLibrary();
		
		Library updateLib = updateLibrary();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(updateLib);
		
		when(libService.getBookById(any())).thenReturn(lib);
		
		this.mockMvc.perform(put("/updateBook/" + lib.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteBookControllerTest() throws Exception {
		
		Library lib = buildLibrary();
		
		when(libService.getBookById(any())).thenReturn(lib);
		doNothing().when(repository).delete(lib);
		
		this.mockMvc.perform(delete("/deleteBook")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\" : \"sfe12\"}"))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string("Book is deleted"));
			
	}
	
	@Test
	public void getAllBooksControllerTest() throws Exception {
		
		Library lib = buildLibrary();
		List<Library> li = new ArrayList<>();
		li.add(lib);
		li.add(lib);
		
		when(repository.findAll()).thenReturn(li);
		
		this.mockMvc.perform(get("/getBooks"))
			.andDo(print())
			.andExpect(status().isOk());
		
		
	}
	
	private Library buildLibrary() {
		
		Library lib = new Library();
		lib.setAisle(12);
		lib.setAuthor("Shety");
		lib.setBook_name("Spring book");
		lib.setIsbn("sfe");
		lib.setId("sfe12");
		
		return lib;
		
	}
	
	private Library updateLibrary() {
		
		Library lib = new Library();
		lib.setAisle(44);
		lib.setAuthor("Rahul Shety");
		lib.setBook_name( "book 5");
		lib.setIsbn("ddd");
		lib.setId("ddd44");
		
		return lib;
		
	}

}
