package com.example.lemmecook_frontend;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //establish connection
    private static final int PORT_NUMBER = 8080;

    // Updated URL with new port
    private static final String URL = "http://192.168.110.233:8000/";
//    private static final String URL = "https://openprojects.pythonanywhere.com/";

    private static Retrofit retrofit = null;

    //public method that returns connection

    public static Retrofit getConnection() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
