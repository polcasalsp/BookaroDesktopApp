package com.bookaro.client.service;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
	
/**
 * @author Pol Casals
 *
 */
public class LoginService {
	
	private static LoginService loginService = new LoginService();	
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();
    final String serverUrl = "http://127.0.0.1:8080";
    private String token = "";
    
    private LoginService() {
    	
    }
    
    public static LoginService getLogin() {
		return loginService;    	
    }
    
   
    
    /**
     * @author Pol Casals
     * @param username
     * @param pw
     * @return
     * @throws IOException
     */
    public void sendCredentials(String username, String pw) throws IOException {
    	String formattedCredentials = userCredentials(username, pw);
    	post(serverUrl + "/api/services/controller/user/login", formattedCredentials);
    }   
    
    
  	/**
  	 * @author Pol Casals
  	 * @param username
  	 * @param pw
  	 * @return
  	 */
      public String userCredentials(String username, String pw) {
      	return "{\"username\":\""+username+"\","
      			+ "\"password\":\""+pw+"\""+"}";
      }   

    /**
     * @author Pol Casals
     * @param url
     * @param formattedCredentials
     * @return
     * @throws IOException
     * @throws InterruptedException 
     */
      public void post(String url, String formattedCredentials) throws IOException {
      	RequestBody body = RequestBody.create(JSON, formattedCredentials);
      	Request request = new Request.Builder()
          .url(url)
          .post(body)
          .build();
  	    try (Response response = client.newCall(request).execute()) {
  	    	token = response.body().string();
  	    }
      }
	
	 public String getToken() {
	    	return token;
	    }
}	