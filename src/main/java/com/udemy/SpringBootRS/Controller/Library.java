package com.udemy.SpringBootRS.Controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Storage2")
public class Library {
	
	@Column(name = "book_name")
	private String book_name;
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "isbn")
	private String isbn;
	
	@Column(name = "author")
	private String author;
	
	@Column(name = "aisle")
	private int aisle;
	
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String bookName) {
		this.book_name = bookName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getAisle() {
		return aisle;
	}
	public void setAisle(int aisle) {
		this.aisle = aisle;
	}
	
	

}
