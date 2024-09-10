package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.health.GoalDataModel
import com.example.lemmecook_frontend.models.response.StatusResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GoalApi {
    @GET("progress/goal")
    fun getUserGoals(
        @Query("user_id") userId: Int
    ): Call<Map<String, GoalDataModel>>

    @POST("progress/goal")
    fun updateUserGoaL(
        @Body goalRequest: GoalDataModel
    ): Call<StatusResponse>
}