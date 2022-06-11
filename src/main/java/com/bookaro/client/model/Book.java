package com.bookaro.client.model;

/**
 * @author Pol Casals
 *
 */
public class Book {	

	private Long id;
	
	private String name, isbn, category, synopsis;

	private Author author;
	
	private Editorial editorial;

	/**
	 * @author Pol Casals
	 * @param id
	 * @param name
	 * @param author
	 * @param isbn
	 * @param category
	 * @param editorial
	 * @param synopsis
	 */
	public Book(Long id, String name, Author author, String isbn, String category, Editorial editorial, String synopsis) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.isbn = isbn;
		this.category = category;
		this.editorial = editorial;
		this.synopsis = synopsis;
	}
	
	/**
	 * @author Pol Casals
	 */
	public Book() {
		
	}

	/**
	 * @author Pol Casals
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @author Pol Casals
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @author Pol Casals
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @author Pol Casals
	 * @return
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * @author Pol Casals
	 * @param author
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @author Pol Casals
	 * @param isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @author Pol Casals
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @author Pol Casals
	 * @return
	 */
	public Editorial getEditorial() {
		return editorial;
	}

	/**
	 * @author Pol Casals
	 * @param editorial
	 */
	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getSynopsis() {
		return synopsis;
	}

	/**
	 * @author Pol Casals
	 * @param synopsis
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	public String getAuthorName() {
		return author.getName();
	}
	
	public String getEditorialName() {
		return editorial.getName();
	}
}