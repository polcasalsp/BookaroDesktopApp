package com.bookaro.client.model;

public class Book {
	

	private Long id;
	
	private String name, author, isbn, category, editorial, synopsis;

	//private Order orderBook;

	public Book(Long id, String name, String author, String isbn, String category, String editorial, String synopsis) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.isbn = isbn;
		this.category = category;
		this.editorial = editorial;
		this.synopsis = synopsis;
	}
	
	public Book() {
		
	}

	// Getter/Setter
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	/*public Order getOrderBook() {
		return orderBook;
	}

	public void setOrderBook(Order orderBook) {
		this.orderBook = orderBook;
	}*/
}