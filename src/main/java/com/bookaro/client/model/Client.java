package com.bookaro.client.model;

import java.util.List;

/** 
 * @author Pedro <br>
 * 
 * Clase Client: 
 * <li> hace referencia a los usuario de tipo cliente </li>
 * <li> Hereda de User.</li>
 * <li> Es la clase con menos permisos.</li>
 * <li> El valor discriminatorio del tiepo de usuario es "CL".</li>
 * <li> Tiene vinculación con la clase Subscription y Order</li>
 *
 */
public class Client extends User {
	
	// ******* Atributos de clase  *******
	
	private Subscription subscription;
	private List<Order> orders; 
	
	/**
	 * Constructor vacio
	 */
	public Client() {}

	//  ******* Getter/Setter ******* 
	
	/**
	 * Getter que devuelve el tipo de subscripcion
	 * @return retorna un objeto de tipo Subscription
	 */
	public Subscription getSubscription() {
		return subscription;
	}

	/**
	 * Setter subscripcion
	 * @param subscription Recibe un parametro de Subscription con la subscripcion
	 */
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	
	/**
	 * Getter Order	  
	 * @return Devuelve una lista de tipo Order
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * Setter Order
	 * @param order Recibe una lista de tipo Order
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	
	
}
