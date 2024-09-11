package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.health.ProgressDataModel
import com.example.lemmecook_frontend.models.response.ProgressGetResponse
import com.example.lemmecook_frontend.models.response.ProgressPostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProgressApi {
    @GET("progress/progress")
    fun getTodayProgress(
        @Query("user_id") userId: Int,
        @Query("date") date: String
    ): Call<ProgressGetResponse>

    @POST("progress/progress")
    fun updateTodayProgress(
        @Body todayProgressRequest: ProgressDataModel
    ): Call<ProgressPostResponse>
}