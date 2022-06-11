	package com.bookaro.client;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookaro.client.Utils.Tools;
import com.bookaro.client.model.Author;
import com.bookaro.client.model.Book;
import com.bookaro.client.model.Client;
import com.bookaro.client.model.Editorial;
import com.bookaro.client.model.Employee;
import com.bookaro.client.model.Order;
import com.bookaro.client.model.Subscription;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.LoginService;
import com.bookaro.client.service.NetClientsService;


@SpringBootTest
class BookaroClientApplicationTests {

	private DBCallService dbcs;
	private LoginService loginService;
	
	/**
	 * Prueba de recuperar la lista de todos los usuarios que
	 * existen en la base de datos estando logeado con el usuario admin.
	 * Devuelve codigo 200, OK.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void getAllUsersPermissionTestAdmin() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		assert dbcs.getUsers().execute().code() == 200;
	}	
	
	/**
	 * Prueba de recuperar la lista de todos los usuarios
	 * que existen en la base de datos con un usuario no administrador.
	 * Devuelve codigo 403, FORBIDDEN.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void getAllUsersPermissionTestUser() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("cliente1", "1234");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		assert dbcs.getUsers().execute().code() == 403;
	}
	
	/**
	 * Prueba el sistema de login y que los roles se recuperan
	 * correctamente a partir del token enviado por el servidor.
	 * Administrador = ROLE_ADMIN
	 * Usuario = ROLE_USER
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void loginAndRoleTest() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		assert Tools.getRoleFromToken().equals("ROLE_ADMIN");
		loginService.sendUserCredentials("cliente1", "1234");
		assert Tools.getRoleFromToken().equals("ROLE_USER");
	}
	
	/**
	 * Prueba el registro de un usuario desde la ventana de login
	 * (antes de logearse con ningun usuario y por tanto sin token)
	 * Devuelve codigo 200, OK.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void userRegistration() throws IOException {
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		Client cli = new Client();
		cli.setUsername("TestUser");		
		cli.setPassword("1234");
		cli.setEmail("test@email.com");
		cli.setName("clienteTest");
		cli.setSurname("test");
		Subscription sub = dbcs.findSubById(1).execute().body();
		cli.setSubscription(sub);
		assert dbcs.newClient(cli).execute().code() == 200;	
	}
	
	/**
	 * Prueba la gestion de la clase cliente estando logeado con un
	 * usuario administrador.
	 * Create, read, update y delete.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void clientCrud() throws IOException {
		//Login with admin user
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		
		//Create a new client
		Client cli = new Client();
		cli.setUsername("crudTestClient");		
		cli.setPassword("1234");
		cli.setEmail("crudTestClient@email.com");
		cli.setName("clientCrudTest");
		cli.setSurname("CrudTestClient");
		Subscription sub = dbcs.findSubById(1).execute().body();
		cli.setSubscription(sub);
		assert dbcs.newClient(cli).execute().code() == 200;		
		cli.setId(dbcs.findByUsername("crudTestClient").execute().body().getId());
		
		//Read created client
		assert dbcs.findClientById(cli.getId()).execute().code() == 200;
	
		
		//Update a client
		cli = dbcs.findClientById(cli.getId()).execute().body();
		assert !cli.getEmail().equals("updated@email.com");
		cli.setEmail("updated@email.com");
		dbcs.updateClient(cli).execute();
		cli = dbcs.findClientById(cli.getId()).execute().body();
		assert cli.getEmail().equals("updated@email.com");
		
		//Delete a client
		dbcs.deleteClient(cli.getId()).execute();
		assertNull(dbcs.findClientById(cli.getId()).execute().body());
	}
	
	/**
	 * Prueba la gestion de la clase employee estando logeado con un
	 * usuario administrador.
	 * Create, read, update y delete.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void employeeCrud() throws IOException {
		//Login with admin user
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		
		//Create a new employee
		Employee emp = new Employee();
		emp.setUsername("crudTestEmployee");		
		emp.setPassword("1234");
		emp.setEmail("crudTestEmployee@email.com");
		emp.setName("employeeCrudTest");
		emp.setSurname("CrudTestEmployee");
		emp.setSalary(500.0);
		assert dbcs.newEmployee(emp).execute().code() == 200;		
		emp.setId(dbcs.findByUsername("crudTestEmployee").execute().body().getId());
		
		//Read created employee
		assert dbcs.findEmployeeById(emp.getId()).execute().code() == 200;
	
		
		//Update a employee
		emp = dbcs.findEmployeeById(emp.getId()).execute().body();
		assert !(emp.getSalary() == 1000.0);
		emp.setSalary(1000.0);
		dbcs.updateEmployeeAdmin(emp).execute();
		emp = dbcs.findEmployeeById(emp.getId()).execute().body();
		assert (emp.getSalary() == 1000.0);
		
		//Delete a employee
		dbcs.deleteEmployee(emp.getId()).execute();
		assertNull(dbcs.findClientById(emp.getId()).execute().body());
	}
	
	/**
	 * Prueba la gestion de la clase libro estando logeado con un
	 * usuario administrador.
	 * Create, read, update y delete.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void bookCrud() throws IOException {
		//Login with admin user
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		
		//Create a new book
		Book book = new Book();
		Author author = dbcs.findAuthorById(1).execute().body();
		Editorial editorial = dbcs.findEditorialById(1).execute().body();
		book.setName("crudTestBook");
		book.setIsbn("crudTestIsbn");	
		book.setAuthor(author);
		book.setEditorial(editorial);
		assert dbcs.newBook(book).execute().code() == 200;		
		
		//Read created book
		assert dbcs.findBookByName(book.getName()).execute().code() == 200;	
		
		//Update a book
		book = dbcs.findBookByName(book.getName()).execute().body();
		assert !book.getName().equals("updatedCrudTestBook");
		book.setName("updatedCrudTestBook");
		dbcs.updateBook(book).execute();
		book = dbcs.findBookByName(book.getName()).execute().body();
		assert book.getName().equals("updatedCrudTestBook");
		
		//Delete a book
		dbcs.deleteBook(dbcs.findBookByName(book.getName()).execute().body().getId()).execute();
		assertNull(dbcs.findBookById(book.getId()).execute().body());
	}
	
	/**
	 * Prueba la gestion de la clase autor estando logeado con un
	 * usuario administrador.
	 * Create, read, update y delete.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void authorCrud() throws IOException {
		//Login with admin user
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		
		//Create a new book
		Author newAuthor = new Author();
		newAuthor.setNacionality("Catalunya");
		newAuthor.setName("Pol Casals");
		assert dbcs.newAuthor(newAuthor).execute().code() == 200;		
		
		//Read created book
		Author author = dbcs.getAuthors().execute().body().stream().filter(a -> a.getName().equals(newAuthor.getName())).findFirst().get();
		assertNotNull(author);
		
		//Update a book
		author.setName("New name");
		dbcs.updateAuthor(author).execute();
		assert dbcs.findAuthorById(author.getId()).execute().body().getName().equals("New name");
		
		//Delete a book
		dbcs.deleteAuthor(author.getId()).execute();
		assertNull(dbcs.findAuthorById(author.getId()).execute().body());
	}
	
	/**
	 * Prueba la gestion de la clase editorial estando logeado con un
	 * usuario administrador.
	 * Create, read, update y delete.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void editorialCrud() throws IOException {
		//Login with admin user
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		
		//Create a new book
		Editorial newEdi = new Editorial();
		newEdi.setName("Nueva Editorial");
		assert dbcs.newEditorial(newEdi).execute().code() == 200;		
		
		//Read created book
		Editorial editorial = dbcs.getEditorials().execute().body().stream().filter(a -> a.getName().equals(newEdi.getName())).findFirst().get();
		assertNotNull(editorial);
		
		//Update a book
		editorial.setName("Updated Name");
		dbcs.updateEditorial(editorial).execute();
		assert dbcs.findEditorialById(editorial.getId()).execute().body().getName().equals("Updated Name");
		
		//Delete a book
		dbcs.deleteEditorial(editorial.getId()).execute();
		assertNull(dbcs.findEditorialById(editorial.getId()).execute().body());
	}
	
	/**
	 * Prueba la gestion de la clase Order estando logeado con un
	 * usuario administrador.
	 * Create, read, update y delete.
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void orderCrud() throws IOException {
		//Login with admin user
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		
		//Create a new Order
		Book book = dbcs.findBookById(1).execute().body();
		Client cli = dbcs.findClientById(4).execute().body();	
		Order order = new Order();
		order.setBook(book);
		order.setActive(true);
		order.setStartDate(new Date());
		order.setClient(cli);
		assert dbcs.newOrder(order).execute().code() == 200;		
		
		//Read Orders
		assert dbcs.getOrders().execute().code() == 200;
		
		//Update an order
	    ArrayList<Order> orders = dbcs.findOrdersByClientId(cli.getId()).execute().body();
	    Order order1 = orders.get(orders.size()-1);
	    order1.setActive(false);
		assert !order1.isActive();
		
		//Delete a book
		dbcs.deleteOrder(order1.getId()).execute();
		assertNull(dbcs.findOrderById(order1.getId()).execute().body());
	}	
	
	/**
	 * Prueba el sistema de logout en el servidor (blacklist)
	 * @author Pol Casals
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	@Test
	void serverLogout() throws IOException, InterruptedException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		String token = loginService.getToken();
		assert !token.equals("");
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		assert dbcs.getUsers().execute().code() == 200;
		dbcs.logout().execute();
		assert dbcs.getUsers().execute().code() == 403;
		Thread.sleep(1000);
	}
	
	/**
	 * Prueba el sistema de logout en el cliente (borrado de token)
	 * @author Pol Casals
	 * @throws IOException
	 */
	@Test
	void clientLogout() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendUserCredentials("admin", "admin");
		String token = loginService.getToken();
		dbcs = NetClientsService.getRetrofitClient().create(DBCallService.class);
		assert !token.equals("");
		loginService.logout();
		token = loginService.getToken();
		assert token.equals("");
	}
}
