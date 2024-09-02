package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.data.RegisterDataModel
import com.example.lemmecook_frontend.models.response.StatusResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UsersApi {
    @POST("users/register")
    fun userRegister(
        @Body registerDataModel: RegisterDataModel
    ): Call<StatusResponse>
}