package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.request.EmailRequest
import com.example.lemmecook_frontend.models.auth.ForgetPasswordDataModel
import com.example.lemmecook_frontend.models.auth.LoginDataModel
import com.example.lemmecook_frontend.models.auth.RegisterDataModel
import com.example.lemmecook_frontend.models.request.UserInfoRequest
import com.example.lemmecook_frontend.models.response.AuthResponse
import com.example.lemmecook_frontend.models.response.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query


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
    ): Call<AuthResponse>

    @POST("users/google_check_user_exist")
    fun googleCheckUserExist(
        @Body checkEmailData : EmailRequest
    ): Call<AuthResponse>

    @GET("users/get_user_info")
    fun getUserInfo(@Query("userId") userId: String): Call<UserInfoResponse>

    @POST("users/update_user_info")
    fun updateUserInfo(
        @Body userInfoRequest : UserInfoRequest
    ): Call<AuthResponse>

//    @Multipart
//    @POST("users/update_user_info")
//    fun updateUserInfo(
//        @Part("userId") userId: RequestBody,
//        @Part("username") username: RequestBody,
//        @Part("fullName") fullName: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("newPassword") newPassword: RequestBody,
//        @Part avatar: MultipartBody.Part?
//    ): Call<AuthResponse>
}