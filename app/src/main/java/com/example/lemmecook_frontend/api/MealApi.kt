package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.health.AllergyDataModel
import com.example.lemmecook_frontend.models.health.DietDataModel
import com.example.lemmecook_frontend.models.request.AllergiesRequest
import com.example.lemmecook_frontend.models.request.DietsRequest
import com.example.lemmecook_frontend.models.request.FavoriteRequest
import com.example.lemmecook_frontend.models.response.AuthResponse
import com.example.lemmecook_frontend.models.response.FavoritesGetResponse
import com.example.lemmecook_frontend.models.response.FavoritesPostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MealApi {
    @GET("meal/allergies")
    fun getAllergies(): Call<Map<String, List<AllergyDataModel>>>

    @GET("meal/diets")
    fun getDiets(): Call<Map<String, List<DietDataModel>>>

    @POST("meal/add_user_allergies")
    fun addUserAllergies(
        @Body allergiesRequest: AllergiesRequest
    ): Call<AuthResponse>

    @POST("meal/add_user_diets")
    fun addUserDiets(
        @Body dietsRequest: DietsRequest
    ): Call<AuthResponse>

    @GET("meal/favorites_view")
    fun getUserFavorites(
        @Query("user_id") userId: Int
    ): Call<FavoritesGetResponse>

    @POST("meal/favorites_view")
    fun addUserFavorites(
        @Body favoriteRequest: FavoriteRequest
    ): Call<FavoritesPostResponse>
}