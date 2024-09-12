package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.request.EmailRequest
import com.example.lemmecook_frontend.models.auth.ForgetPasswordDataModel
import com.example.lemmecook_frontend.models.auth.LoginDataModel
import com.example.lemmecook_frontend.models.auth.RegisterDataModel
import com.example.lemmecook_frontend.models.response.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface UsersApi {
    @POST("users/register")
    fun userRegister(
        @Body registerDataModel: RegisterDataModel
    ): Call<AuthResponse>

    @POST("users/login")
    fun userLogin(
        @Body loginDataModel: LoginDataModel
    ): Call<AuthResponse>

    @POST("users/forget_password")
    fun userForgetPassword(
        @Body forgetPasswordDataModel: ForgetPasswordDataModel
    ): Call<AuthResponse>

    @POST("users/check_email")
    fun userCheckEmail(
        @Body checkEmailData : EmailRequest
    ): Call<StatusResponse>

    @POST("users/google_check_user_exist")
    fun googleCheckUserExist(
        @Body checkEmailData : EmailRequest
    ): Call<StatusResponse>
}