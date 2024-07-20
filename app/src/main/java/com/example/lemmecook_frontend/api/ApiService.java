package com.example.lemmecook_frontend.api;

import com.example.lemmecook_frontend.models.Model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("tasks/")
    Call<Model> createTask(@Body Model task);

}
