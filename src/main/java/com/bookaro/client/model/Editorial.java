package com.bookaro.client.model;

import java.util.List;

public class Editorial {
	
	private Long id;
	private String name;
	private List<Book> books;
	
	public Editorial() {}

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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	

}
