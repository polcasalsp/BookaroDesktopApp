package com.bookaro.client.service;


import java.util.ArrayList;

import com.bookaro.client.model.Book;
import com.bookaro.client.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Lista de llamadas al servidor para recuperar los datos
 * que precisen las vistas para rellenar los campos/tablas.
 * @author Pol Casals
 *
 */
public interface DBCallService {	
	
	@GET("/api/user/all")
	Call<ArrayList<User>> getUsers();

	@GET("/api/user/{id}")
	Call<User> findById(@Path(value = "id", encoded = true) int userId);
	
	@GET("/api/user/username/{name}")
	Call<User> findByUsername(@Path(value = "name", encoded = true) String username);
	
	@GET("/api/book")
	Call<ArrayList<Book>> getBooks();
	
	@POST("/api/user/logout") 
	Call<String> logout();
}