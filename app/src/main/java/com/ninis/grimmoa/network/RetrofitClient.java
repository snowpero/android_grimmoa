package com.ninis.grimmoa.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ninis.grimmoa.define.URLDefines;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gypark on 2017. 3. 21..
 */

public class RetrofitClient {
    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;
    private static OkHttpClient okHttpClient;
    private static ApiInterface apiInterface;
    private static Gson gson;

    public synchronized static ApiInterface getInstance() {
        if( apiInterface == null ) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();

//            gson = new GsonBuilder()

            apiInterface = new Retrofit.Builder()
                    .baseUrl(URLDefines.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiInterface.class);
        }

        return apiInterface;
    }
}
