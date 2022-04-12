package com.bookaro.client.service;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
	
public class LoginService {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    final OkHttpClient client = new OkHttpClient();
    final String serverUrl = "http://127.0.0.1:8080";
    
    public String sendCredentials(String username, String pw) throws IOException {
    	String formattedCredentials = userCredentials(username, pw);
    	String response = post(serverUrl + "/api/services/controller/user/login", formattedCredentials);
    	return response.split(" ")[1];
    }    

	public String post(String url, String formattedCredentials) throws IOException {
    	RequestBody body = RequestBody.create(JSON, formattedCredentials);
    	Request request = new Request.Builder()
        .url(url)
        .post(body)
        .build();
	    try (Response response = client.newCall(request).execute()) {
	    	return response.body().string();
	    }
    }
  
    public String userCredentials(String username, String pw) {
    	return "{\"username\":\""+username+"\","
    			+ "\"password\":\""+pw+"\""+"}";
    }    
}	