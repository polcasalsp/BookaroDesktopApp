package com.bookaro.client.service;

import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.model.Author;
import com.bookaro.client.model.Book;
import com.bookaro.client.model.Client;
import com.bookaro.client.model.Editorial;
import com.bookaro.client.model.Employee;
import com.bookaro.client.model.Order;
import com.bookaro.client.model.Subscription;
import com.bookaro.client.model.User;

import javafx.scene.image.Image;
import okhttp3.MultipartBody;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
	
	@GET("/api/author/all")
	Call<ArrayList<Author>> getAuthors();
	
	@GET("/api/editorial/all")
	Call<ArrayList<Editorial>> getEditorials();
	
	
	//Save new entities
	@POST("/api/employee/insert")
	Call<String> newEmployee(@Body Employee emp);
	
	@POST("/api/client/insert")
	Call<String> newClient(@Body Client cli);
	
	@POST("/api/book/insert")
	Call<String> newBook(@Body Book book);
	
	@POST("/api/author/insert")
	Call<String> newAuthor(@Body Author author);
	
	@POST("/api/editorial/insert")
	Call<String> newEditorial(@Body Editorial editorial);
	
	@POST("/api/order/insert")
	Call<String> newOrder(@Body Order order);
	
	@Multipart
	@POST("/api/book/upload/image")
	Call<Image> newImage(@Part MultipartBody.Part img,
						 @Part("book_id") long bookId);
 	
	
	//Get entities of a given type by id calls
	@GET("/api/user/{id}")
	Call<User> findUserById(@Path(value = "id", encoded = true) long userId);
	
	@GET("/api/client/{id}")
	Call<Client> findClientById(@Path(value = "id", encoded = true) long clientId);
	
	@GET("/api/employee/{id}")
	Call<Employee> findEmployeeById(@Path(value = "id", encoded = true) long employeeId);
	
	@GET("/api/subscription/id/{id}")
	Call<Subscription> findSubById(@Path(value = "id", encoded = true) long subId);
	
	@GET("/api/book/get/image/id/{id}")
	Call<Image> findImageById(@Path(value = "id", encoded = true) long imgId);
	
	@GET("/api/author/id/{id}")
	Call<Author> findAuthorById(@Path(value = "id", encoded = true) long authorId);
	
	@GET("/api/book/{id}")
	Call<Book> findBookById(@Path(value = "id", encoded = true) long bookId);
	
	@GET("/api/order/id/{id}")
	Call<Order> findOrderById(@Path(value = "id", encoded = true) long orderId);
	
	@GET("/api/editorial/id/{id}")
	Call<Editorial> findEditorialById(@Path(value = "id", encoded = true) long editorialId);
	
	
	//Get entities of a given type by name calls
	@GET("/api/user/username/{name}")
	Call<User> findByUsername(@Path(value = "name", encoded = true) String username);
	
	@GET("/api/client/username/{name}")
	Call<Client> findClientByUsername(@Path(value = "name", encoded = true) String username);
	
	@GET("/api/book/name/{name}")
	Call<Book> findBookByName(@Path(value = "name", encoded = true) String name);

	
	//Update entities
	@PUT("/api/client/updateA")
	Call<String> updateClientAdmin(@Body Client client);
	
	@PUT("/api/employee/updateA")
	Call<String> updateEmployeeAdmin(@Body Employee employee);
	
	@PUT("/api/client/update")
	Call<String> updateClient(@Body Client client);
	
	@PUT("/api/employee/update")
	Call<String> updateEmployee(@Body Employee employee);
	
	@PUT("/api/order/update")
	Call<String> updateOrder(@Body Order order);
	
	@PUT("/api/subscription/update")
	Call<String> updateSubscription(@Body Subscription subscription);
	
	@PUT("/api/book/update")
	Call<String> updateBook(@Body Book book);
	
	@PUT("/api/editorial/update")
	Call<String> updateEditorial(@Body Editorial editorial);
	
	@PUT("/api/author/update")
	Call<String> updateAuthor(@Body Author author);
	
	@PUT("/api/image/update")
	Call<String> updateImage(@Body Image image);
		
	
	//Delete entities
	@DELETE("/api/employee/{id}")
	Call<String> deleteEmployee(@Path(value = "id") long empId);
	
	@DELETE("/api/client/{id}")
	Call<String> deleteClient(@Path(value = "id") long cliId);
	
	@DELETE("/api/book/{id}")
	Call<String> deleteBook(@Path(value = "id") long bookId);
	
	@DELETE("/api/order/{id}")
	Call<String> deleteOrder(@Path(value = "id") long orderId);
	
	@DELETE("/api/author/delete/{id}")
	Call<String> deleteAuthor(@Path(value = "id") long authorId);
	
	@DELETE("/api/editorial/delete/{id}")
	Call<String> deleteEditorial(@Path(value = "id") long editorialId);
	
	
	@GET("/api/order/client")
	Call<ArrayList<Order>> findOrdersByClient(@Body Client client);
	
	@GET("/api/client/orders/{id}")
	Call<ArrayList<Order>> findOrdersByClientId(@Path(value = "id") long clientId);
	
	//Get image
    public static Image getImageByName(String name) {
		okhttp3.Response response = null;
		try {
			response = NetClientsService.getHttpClient().newCall(
					new Request.Builder()
					.url("https://127.0.0.1:8443/api/book/get/image/" + name +".jpg")
					.build()).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Image(response.body().byteStream());
	}
	
	@POST("/api/user/logout") 
	Call<String> logout();
}
