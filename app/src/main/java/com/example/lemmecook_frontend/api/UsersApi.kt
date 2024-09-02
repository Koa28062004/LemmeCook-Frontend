package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.data.ForgetPasswordDataModel
import com.example.lemmecook_frontend.models.data.LoginDataModel
import com.example.lemmecook_frontend.models.data.RegisterDataModel
import com.example.lemmecook_frontend.models.response.StatusResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface UsersApi {
    @POST("users/register")
    fun userRegister(
        @Body registerDataModel: RegisterDataModel
    ): Call<StatusResponse>

    @POST("users/login")
    fun userLogin(
        @Body loginDataModel: LoginDataModel
    ): Call<StatusResponse>

    @POST("users/forget_password")
    fun userForgetPassword(
        @Body forgetPasswordDataModel: ForgetPasswordDataModel
    ): Call<StatusResponse>


}