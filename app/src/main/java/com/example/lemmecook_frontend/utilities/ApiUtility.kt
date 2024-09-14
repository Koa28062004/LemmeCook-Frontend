package com.example.lemmecook_frontend.utilities

import android.content.Context
import com.example.lemmecook_frontend.api.UsersApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtility {
    companion object {
        private val BASE_URL = "http://10.0.2.2:8000/"

        private fun getBaseUrl(debug : Boolean = false): String {
            if(debug){
                return "Meow Meow"
            }
            return BASE_URL
        }

        fun getApiClient(debug: Boolean = false): Retrofit {
            return Retrofit.Builder()
                .baseUrl(getBaseUrl(debug))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}