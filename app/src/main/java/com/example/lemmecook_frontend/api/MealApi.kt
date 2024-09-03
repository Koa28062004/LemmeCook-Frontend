package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.data.AllergyDataModel
import com.example.lemmecook_frontend.models.data.DietDataModel
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("meal/allergies")
    fun getAllergies(): Call<Map<String, List<AllergyDataModel>>>

    @GET("meal/diets")
    fun getDiets(): Call<List<DietDataModel>>
}