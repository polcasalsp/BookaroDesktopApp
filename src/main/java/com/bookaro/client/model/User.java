package com.bookaro.client.model;

/**
 * @author Pol Casals
 *
 */
public class User {	
	private Long id;	
	private String username;
	private String password;
	private String email;
	private String dni;
	private String name;
	private String surname;
	private String address;
	private String roles;
	private int age;
	private boolean active;
		
	/**
	 * @author Pol Casals
	 * @param id
	 * @param username
	 * @param password
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param role
	 */
	public User(Long id_user, String username, String password, 
		    String name, String surname, String dni, String address,
		    String email, int age, String roles, boolean active) {		
		this.id = id_user;
		//this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.dni = dni;
		this.address = address;
		this.email = email;
		this.age = age;		
		this.roles = roles;
		this.active = active;
	}
	
	/**
	 * @author Pol Casals
	 */
	public User() {
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
	public String getUsername() {
		return username;
	}
	
	/**
	 * @author Pol Casals
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @author Pol Casals
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @author Pol Casals
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @param firstName
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * @author Pol Casals
	 * @param lastName
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getRole() {
		return roles;
	}
	
	/**
	 * @author Pol Casals
	 * @param role
	 */
	public void setRole(String roles) {
		this.roles = roles;
	}
	
	/**
	 * Getter DNI
	 * @return Devuelve el dni en string
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Setter dni
	 * @param dni recibe el dni como string
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Setter direccion del usuario
	 * @return retorna la direccion
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter direccion
	 * @param address recibe la dirección como string
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter edad 
	 * @return retorna la edad del usuario
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Setter edad
	 * @param age recibe un int con la edad
	 */
	public void setAge(int age) {
		this.age = age;
	}	
	
	/**
	 * Metodo que obtiene si un User está activo o no
	 * @return Retorna true o false
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Metodo para modificar el estado de un usuario
	 * @param active Recibe un parametro boolean
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
}
