package com.example.lemmecook_frontend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.List;
import retrofit2.http.GET;

public interface ApiService {
    @POST("tasks/")
    Call<Model> createTask(@Body Model task);

}
