package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.health.GoalDataModel
import com.example.lemmecook_frontend.models.response.AuthResponse
import com.example.lemmecook_frontend.models.response.GoalGetResponse
import com.example.lemmecook_frontend.models.response.GoalPostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GoalApi {
    @GET("progress/goal")
    fun getUserGoals(
        @Query("user_id") userId: Int
    ): Call<GoalGetResponse>

    @POST("progress/goal")
    fun updateUserGoaL(
        @Body goalRequest: GoalDataModel
    ): Call<GoalPostResponse>
}