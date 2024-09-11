package com.example.lemmecook_frontend.utilities

import android.content.Context
import android.widget.Toast
import com.example.lemmecook_frontend.api.MealApi
import com.example.lemmecook_frontend.models.request.FavoriteRequest
import com.example.lemmecook_frontend.models.response.AuthResponse
import com.example.lemmecook_frontend.models.response.FavoritesGetResponse
import com.example.lemmecook_frontend.models.response.FavoritesPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteApiUtility {
    companion object {
        // Function to handle adding a recipe to favorites
        fun addToFavorites(
            userId: Int,
            mealId: Int,
            context: Context
        ): () -> Unit {
            return {
                val favoritesApi = ApiUtility.getApiClient().create(MealApi::class.java)
                val favoritesRequest = FavoriteRequest (
                    user_id = userId,
                    meal_id = mealId
                )

                // Make the POST request to add the recipe to favorites
                favoritesApi.addUserFavorites(favoritesRequest).enqueue(
                    object : Callback<FavoritesPostResponse> {
                        override fun onResponse(
                            call: Call<FavoritesPostResponse>,
                            response: Response<FavoritesPostResponse>
                        ) {
                            if (response.isSuccessful) {
                                // Show a success message to the user
                                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                            } else {
                                // Handle the error
                                Toast.makeText(context, "Failed to add to favorites", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<FavoritesPostResponse>, t: Throwable) {
                            // Handle the failure
                            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }

        // Function to get favorite meals
        fun getFavoriteMeals(userId: Int, context: Context, onSuccess: (List<Int>) -> Unit, onError: (String) -> Unit) {
            val favoritesApi = ApiUtility.getApiClient().create(MealApi::class.java)

            favoritesApi.getUserFavorites(userId).enqueue(object : Callback<FavoritesGetResponse> {
                override fun onResponse(call: Call<FavoritesGetResponse>, response: Response<FavoritesGetResponse>) {
                    if (response.isSuccessful) {
                        val favoritesResponse = response.body()
                        if (favoritesResponse != null && favoritesResponse.status == "success") {
                            val favorites = favoritesResponse.favorites ?: emptyList()
                            onSuccess(favorites)
                        } else {
                            onError(favoritesResponse?.message ?: "Failed to get favorites")
                        }
                    } else {
                        onError("Failed to get favorites")
                    }
                }

                override fun onFailure(call: Call<FavoritesGetResponse>, t: Throwable) {
                    onError(t.message ?: "An unknown error occurred")
                }
            })
        }
    }
}