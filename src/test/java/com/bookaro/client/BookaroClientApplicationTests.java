package com.bookaro.client;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookaro.client.Utils.RetrofitClient;
import com.bookaro.client.model.Book;
import com.bookaro.client.service.DBCallService;
import com.bookaro.client.service.LoginService;

import retrofit2.Response;

@SpringBootTest
class BookaroClientApplicationTests {

	private DBCallService dbcs;
	private LoginService loginService;
	
	@Test
	void contextLoads() throws IOException {
		loginService = LoginService.getLogin();
		loginService.sendCredentials("admin", "admin");
		String token = loginService.getToken();
		dbcs = RetrofitClient.getClient(token).create(DBCallService.class);
		
		Response<ArrayList<Book>> findBooks = dbcs.getBooks().execute();
		for (Book book: findBooks.body()) {
			System.out.println(book.getName());
		}
		assert !findBooks.body().isEmpty();
		assert findBooks.body().get(0).getName().equals("Game of Thrones1");
	}
}