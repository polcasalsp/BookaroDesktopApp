package com.bookaro.client.service;
import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

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
public class NetClientsService {
	
	private static Retrofit retrofit = null;
	private final static String serverUrl = "https://127.0.0.1:8443";
	private final static String headerTitle = "Authorization";
	private final static String headerName = "Bearer ";
	
	/**
	 * Crea una instancia de Interceptor que será responsable de incluir
	 * el token JWT recibido como parámetro a todas las futuras peticiones
	 * al servidor y lo incluye al cliente http
	 * @author Pol Casals
	 * @return
	 */
	public static OkHttpClient getHttpClient() {
		
		String token = LoginService.getLogin().getToken();
		
	    Interceptor interceptor = new Interceptor() {
	        @Override
	        public Response intercept(Chain chain) throws IOException {
	            Request request = chain.request()
	            		.newBuilder()
	            		.addHeader(headerTitle, headerName + token)
	            		.build();
	            return chain.proceed(request);
	        }
	    };
		
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.addInterceptor(interceptor);
		builder.hostnameVerifier(new HostnameVerifier() {
		    @Override
		    public boolean verify(String hostname, SSLSession session) {
		        return true;
		    }
		});
		OkHttpClient client = builder.build();
		return client;
	}
	
	
	
	/**
 	 * Construye una instancia de retrofit que usa el cliente http y un conversor 
 	 * de Objetos a JSON y viceversa para el intercambio de datos y realización 
 	 * de peticiones cliente-servidor.
	 * @author Pol Casals
	 * @return
	 * @throws IOException
	 */
	public static Retrofit getRetrofitClient() throws IOException {			
		Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd").create();

		OkHttpClient client = getHttpClient();	
		
		retrofit = new Retrofit.Builder()
				.baseUrl(serverUrl)
				.addConverterFactory(ScalarsConverterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(client)
				.build();
		return retrofit;
	}	
}