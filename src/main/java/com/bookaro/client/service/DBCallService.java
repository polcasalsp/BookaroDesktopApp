package com.bookaro.client.service;


import java.io.IOException;
import java.util.ArrayList;

import com.bookaro.client.model.Book;
import com.bookaro.client.model.Client;
import com.bookaro.client.model.User;

import javafx.scene.image.Image;
import okhttp3.Request;
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
	
	//Get all entities of a given type calls
	@GET("/api/user/all")
	Call<ArrayList<User>> getUsers();
	
	@GET("/api/book/all")
	Call<ArrayList<Book>> getBooks();
	
	@GET("/api/client/all")
	Call<ArrayList<Client>> getClients();
	
	
	//Get entities of a given type by id calls
	@GET("/api/user/{id}")
	Call<User> findUserById(@Path(value = "id", encoded = true) int userId);
	
	@GET("/api/client/{id}")
	Call<Client> findClientById(@Path(value = "id", encoded = true) int clientId);
	
	
	//Get entities of a given type by name calls
	@GET("/api/user/username/{name}")
	Call<User> findByUsername(@Path(value = "name", encoded = true) String username);	
	
	
	
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