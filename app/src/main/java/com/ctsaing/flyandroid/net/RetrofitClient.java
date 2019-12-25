package com.ctsaing.flyandroid.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 建造Retrofit
 */
public class RetrofitClient {

	private static RetrofitClient instance = null;
	private APIService apiService;

	public static RetrofitClient getInstance() {
		if (instance == null){
			synchronized (RetrofitClient.class){
				if (instance == null)
					instance = new RetrofitClient();
			}
		}
		return instance;
	}


	public APIService getApiService(){
		//初始化一个Client
		OkHttpClient okHttpClient = new OkHttpClient();

		apiService = new Retrofit.Builder().baseUrl(Urls.baseUrl)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build()
				.create(APIService.class);
		return apiService;
	}


}
