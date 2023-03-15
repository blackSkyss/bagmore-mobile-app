package com.example.bagmore.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String baseURL = "http://10.0.2.2:5252/api/";
    private static Retrofit retrofit;

    public static final String AUTH_HEADER = "Authorization";

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
