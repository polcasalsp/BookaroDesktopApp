package com.bookaro.client.model;

import java.util.List;

public class Author {
	
	private Long id;	
	private String name; 
	private String nacionality;
	private List<Book> books;

	public Author() {}

	
	// ********* Getters/Setters ********* //
	
	public Long getId() {
		return id;
	}	
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNacionality() {
		return nacionality;
	}

	public void setNacionality(String nacionality) {
		this.nacionality = nacionality;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}	
	

}
