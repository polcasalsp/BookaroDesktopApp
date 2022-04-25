package com.bookaro.client.Utils;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Pol Casals
 *
 */
public class RetrofitClient {
	
	private static Retrofit retrofit = null;
	private final static String serverUrl = "http://127.0.0.1:8080";
	private final static String headerTitle = "Authorization";
	private final static String headerName = "Bearer ";
	
	/**
	 * Crea una instancia de Interceptor que será responsable de incluir
	 * el token JWT recibido como parámetro a todas las futuras peticiones
	 * al servidor, lo incluye al cliente http y finalmente se construye una
	 * instancia de retrofit que usa el cliente http y un conversor de Objetos
	 * a JSON y viceversa para el intercambio de datos y realización de peticiones
	 * cliente-servidor.
	 * @author Pol Casals
	 * @param token
	 * Token JWT que envia el servidor como respuesta una vez ha recibido 
	 * un conjunto (Usuario y Contraseña) de credenciales correctas.
	 * @return
	 * @throws IOException
	 */
	public static Retrofit getClient(String token) throws IOException {	
		
	    //Interceptor that adds JWT token to future requests
	    Interceptor interceptor = new Interceptor() {
	        @Override
	        public Response intercept(Chain chain) throws IOException {
	            Request request = chain.request().newBuilder().addHeader(headerTitle, headerName + token).build();
	            return chain.proceed(request);
	        }
	    };
		
		//Http client to handle requests and responses to/from server
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.addInterceptor(interceptor);
		OkHttpClient client = builder.build();		
		
		//Json to POJO converter and viceversa
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		retrofit = new Retrofit.Builder()
				.baseUrl(serverUrl)
				.addConverterFactory(ScalarsConverterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(client)
				.build();
		return retrofit;
	}	
}