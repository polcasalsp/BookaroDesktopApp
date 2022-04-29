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
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final String serverUrl = "http://127.0.0.1:8080";
    private String token = "";
    
    private LoginService() { }
    
    public static LoginService getLogin() {
		return loginService;    	
    }
    
	public String getToken() {
   	    return token;
    }
   
    
    /**
     * @author Pol Casals
     * @param username
     * El valor del campo Usuario en la vista login, introducido por el usuario
     * que solicita el inicio de sesión.
     * @param pw
     * El valor del campo Contraseña en la vista login, introducido por el usuario
     * que solicita el inicio de sesión.
     * @throws IOException
     */
    public void sendUserCredentials(String username, String pw) throws IOException {
    	String formattedCredentials = userCredentials(username, pw);
    	postLogin(serverUrl + "/api/user/login", formattedCredentials);
    }   
    
    
  	/**
  	 * Recibe las credenciales introducidas por el usuario y les da 
  	 * formato JSON para que el servidor las pueda interpretar y validar.
  	 * @author Pol Casals
  	 * @param username
  	 * @param pw
  	 */
      private String userCredentials(String username, String pw) {
      	return "{\"username\":\""+username+"\","
      			+ "\"password\":\""+pw+"\""+"}";
      }   

    /**
     * Crea una petición con las credenciales recibidas y formateadas,
     * es enviada al servidor {@url} y se espera recibir el token JWT 
     * en la respuesta.
     * @author Pol Casals
     * @param url
     * Es la dirección del servidor.
     * @param formattedCredentials
     * Credenciales correctamente formateadas que recibirá el servidor.
     * @throws IOException
     * @throws InterruptedException 
     */
      private void postLogin(String url, String formattedCredentials) throws IOException {
      	RequestBody body = RequestBody.create(JSON, formattedCredentials);
      	Request request = new Request.Builder()
          .url(url)
          .post(body)
          .build();
  	    try (Response response = client.newCall(request).execute()) {
  	    	token = response.body().string().split(" ")[1];
  	    }
      }
      
      /**
       * Borra el token cuando el usuario quiere salir de la aplicación.
       * @author Pol Casals
       */
      public void logout() {
    	  token = "";
      }
}	