package com.example.lemmecook_frontend.api;

import com.example.lemmecook_frontend.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRecipeJava {
    @GET("recipes/findByIngredients")
    Call<List<Recipe>> getRecipes(
            @Query("ingredients") String ingredients,
            @Query("number") int number,
            @Query("apiKey") String apiKey,
            @Query("diet") String diet // New vegan parameter
    );
}
