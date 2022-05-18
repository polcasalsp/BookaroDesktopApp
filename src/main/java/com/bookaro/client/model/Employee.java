package com.bookaro.client.model;


/**
 * @author Pedro<br>
 * 
 * Clase Employee: <br>
 * <li> Es un tipo de usuario. Hereada de la clase User.</li>
 * <li> El valor discriminatorio es "EM".</li>
 *
 */

public class Employee extends User {

	// ******* Atributos de clase *******
	private String position;
	private double salary;
	
	/**
	 * Constructor vacio.
	 */
	public Employee() {}	

	
	// ******* Getter/Setter *******
	
	/**
	 * Getter salary
	 * @return Retorna un double con el salario del empleado
	 */
	public double getSalary() {
		return salary;
	}
	
	/**
	 * Setter salary
	 * @param salary Recibe un parámetro de tipo double
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * Getter position
	 * @return retorna un string con la posicion del empleado.
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Setter position
	 * @param position Recibe un string con la posicion del empleado.
	 */
	public void setPosition(String position) {
		this.position = position;
	}	
	
	
		
}
