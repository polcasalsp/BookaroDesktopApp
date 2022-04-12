package com.bookaro.client.service;


import java.util.ArrayList;

import com.bookaro.client.model.Book;
import com.bookaro.client.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DBCallService {	
	
	@GET("/api/services/controller/users")
	Call<ArrayList<User>> getUsers();

	@GET("/api/services/controller/users/id/{user_id}")
	Call<User> findById(@Path(value = "user_id", encoded = true) int userId);
	
	@GET("/api/services/controller/users/username/{name}")
	Call<User> findByUsername(@Path(value = "name", encoded = true) String username);
	
	@GET("/api/book")
	Call<ArrayList<Book>> getBooks();
}