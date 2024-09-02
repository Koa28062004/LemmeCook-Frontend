package com.example.lemmecook_frontend.utilities

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtility {
    private val BASE_URL = "http://your-django-backend-url/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}