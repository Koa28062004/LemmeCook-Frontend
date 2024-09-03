package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.data.AllergyDataModel
import com.example.lemmecook_frontend.models.data.DietDataModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MealApi {
    @GET("meal/allergies")
    fun getAllergies(): Call<Map<String, List<AllergyDataModel>>>

    @GET("meal/diets")
    fun getDiets(): Call<Map<String, List<DietDataModel>>>

    @POST("meal/add_user_allergies/")
    fun addUserAllergies(
        @Body requestBody: Map<String, Any>
    ): Call<Void>
}