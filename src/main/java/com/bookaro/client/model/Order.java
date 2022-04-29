package com.bookaro.client.model;

import java.util.Date;
import java.util.List;


/**
 * 
 * @author Pedro<br>
 * Clase Order:
 * <li> Gestiona los alquileres de libros.</li>
 * <li> Tiene vinculación con las clases Client y Book</li>
 *
 */
public class Order {
	
	// ****** Atributos de clase ****** 
	
	private Long id;
	
	private Date startDate;
	private boolean active;	
	private Client client;
	private List<Book> books; 
	private Book book;	

	/**
	 * Métodeo para contar order activas
	 * <b> Aún por implantar </b>
	 * @param clientId Recibe el id del cliente
	 * @return Retorna true o false dependien de la cantidad de libros adquiridos.
	 */
	public boolean countActiveOrders (long clientId) {	
		return false; // aún por implementar
	}
	
	
	// ******  Getter/Setter ******	
	
	/**
	 * Getter startDate
	 * @return Retorna un Date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Setterr startDate
	 * @param startDate Recibe un parametro de tipo Date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter active
	 * @return Retorna true o false en funcion de si esta activa o no.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Setter active
	 * @param active Recibe un boolean para activar o desactivar la order
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Getter books
	 * @return Retorna una lista de libros asociados a la order
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Setter books
	 * @param books Recibe una lista de libros
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	/**
	 * Getter ID
	 * @return Retorna un Long con el ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter ID
	 * @param id Recibe el id como un Long
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * Getter clients
	 * @return Retorna un Objeto Client
	 */
	public Client getClient() {
		return client;
	}


	/**
	 * Setter clients
	 * @param clients Recibe un objeto Client
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	
	
	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}
	
		
}
