package com.bookaro.client.Utils;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
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
	
	/**
	 * @author Pol Casals
	 * @param token
	 * @return
	 * @throws IOException
	 */
	public static Retrofit getClient(String token) throws IOException {	
		
	    //Interceptor that adds JWT token to future requests
	    Interceptor interceptor = new Interceptor() {
	        @Override
	        public Response intercept(Chain chain) throws IOException {
	            Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
	            return chain.proceed(request);
	        }
	    };
	    
		//Interceptor that logs Http messages to console
		HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
		logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
		
		//Http client to handle requests and responses to/from server
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.addInterceptor(interceptor);
		//.addInterceptor(logInterceptor);
		OkHttpClient client = builder.build();		
		
		//Json to POJO converter and viceversa
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		//
		retrofit = new Retrofit.Builder()
				.baseUrl(serverUrl)
				.addConverterFactory(ScalarsConverterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson))
				.client(client)
				.build();
		return retrofit;
	}	
}