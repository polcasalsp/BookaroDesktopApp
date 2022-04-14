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
	private String firstName;
	private String lastName;
	private String role;
		
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
	public User(Long id, String username, String password, String email, String firstName, String lastName,
			String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
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
	
	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @author Pol Casals
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @author Pol Casals
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @author Pol Casals
	 * @return
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * @author Pol Casals
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
