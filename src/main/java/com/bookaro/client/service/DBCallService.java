package com.bookaro.client.service;


import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.model.Book;
import com.bookaro.client.model.Client;
import com.bookaro.client.model.Employee;
import com.bookaro.client.model.Order;
import com.bookaro.client.model.Subscription;
import com.bookaro.client.model.User;

import javafx.scene.image.Image;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Lista de llamadas al servidor para recuperar los datos
 * que precisen las vistas para rellenar los campos/tablas.
 * @author Pol Casals
 *
 */
public interface DBCallService {	
	
	//Get all entities of a given type calls
	@GET("/api/user/all")
	Call<ArrayList<User>> getUsers();
	
	@GET("/api/book/all")
	Call<ArrayList<Book>> getBooks();
	
	@GET("/api/client/all")
	Call<ArrayList<Client>> getClients();
	
	@GET("/api/employee/all")
	Call<ArrayList<Employee>> getEmployees();
	
	@GET("/api/order/all")
	Call<ArrayList<Order>> getOrders();
	
	
	//Save new entities
	@POST("/api/employee/insert")
	Call<String> newEmployee(@Body Employee emp);
	
	@POST("/api/client/insert")
	Call<String> newClient(@Body Client cli);
	
	@POST("/api/book/insert")
	Call<String> newBook(@Body Book book);
	
	
	//Get entities of a given type by id calls
	@GET("/api/user/{id}")
	Call<User> findUserById(@Path(value = "id", encoded = true) long userId);
	
	@GET("/api/client/{id}")
	Call<Client> findClientById(@Path(value = "id", encoded = true) long clientId);
	
	@GET("/api/employee/{id}")
	Call<Employee> findEmployeeById(@Path(value = "id", encoded = true) long employeeId);
	
	@GET("/api/subscription/{id}")
	Call<Subscription> findSubById(@Path(value = "id", encoded = true) long subId);
	
	
	//Get entities of a given type by name calls
	@GET("/api/user/username/{name}")
	Call<User> findByUsername(@Path(value = "name", encoded = true) String username);
	
	@GET("/api/book/name/{name}")
	Call<Book> findBookByName(@Path(value = "name", encoded = true) String name);

	
	//Save entities
	@PUT("/api/client/update")
	Call<String> updateClient(@Body Client client);
	
	@PUT("/api/employee/update")
	Call<String> updateEmployee(@Body Employee employee);
	
	@PUT("/api/book/update")
	Call<String> updateBook(@Body Book book);
		
	
	//Delete entities
	@DELETE("/api/employee/{id}")
	Call<String> deleteEmployee(@Path(value = "id") long empId);
	
	@DELETE("/api/client/{id}")
	Call<String> deleteClient(@Path(value = "id") long cliId);
	
	@DELETE("/api/book/{id}")
	Call<String> deleteBook(@Path(value = "id") long bookId);
	
	@DELETE("/api/order/{id}")
	Call<String> deleteOrder(@Path(value = "id") long orderId);
	
	
	@POST("/api/user/logout") 
	Call<String> logout();
	
	
	public static Image getImageByName(String name) {
		okhttp3.Response response = null;
		try {
			response = NetClientsService.getHttpClient().newCall(
					new Request.Builder()
					.url("http://127.0.0.1:8080/api/book/get/image/" + name)
					.build()).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Image(response.body().byteStream());
	}	
}
