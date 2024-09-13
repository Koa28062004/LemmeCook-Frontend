package com.example.lemmecook_frontend.api

import com.example.lemmecook_frontend.models.recipe.RecipeInformation
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Data class for recipe search results
@Serializable
data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String
)

interface RecipeService {
    @GET("recipes/findByIngredients")
    suspend fun searchRecipesByIngredients(
        @Query("apiKey") apiKey: String,
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 10
    ): List<Recipe>

    @GET("recipes/{id}/information")
    fun getRecipeInformation(
        @Path("id") id: Int, //get id from searchRecipesByIngredients
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean = true,
        @Query("addWinePairing") addWinePairing: Boolean = false,
        @Query("addTasteData") addTasteData: Boolean = false
    ): Call<RecipeInformation>
}

// if the above interface returns null, try the following:
//interface RecipeService {
//    @GET("recipes/findByIngredients?apiKey=") // ib Linh for apiKey
//    suspend fun searchRecipesByIngredients(
//        @Query("apiKey") apiKey: String,
//        @Query("ingredients") ingredients: String,
//        @Query("number") number: Int = 10
//    ): List<Recipe>
//
//    @GET("recipes/{id}/information?apiKey=") // ib Linh for apiKey
//    suspend fun getRecipeInformation(
//        @Query("apiKey") apiKey: String,
//        @Path("id") id: Int, //get id from searchRecipesByIngredients
//        @Query("includeNutrition") includeNutrition: Boolean = false,
//        @Query("addWinePairing") addWinePairing: Boolean = false,
//        @Query("addTasteData") addTasteData: Boolean = false
//    ): RecipeInformation
//}

object RetrofitInstance {
    private val contentType = "application/json".toMediaType()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    val recipeService: RecipeService by lazy {
        retrofit.create(RecipeService::class.java)
    }
}

fun test() = runBlocking {
    val apiKey = "YOUR_API_KEY" // ib Linh for apiKey

    try {
        //test searchRecipesByIngredients with ingredients "tomato,cheese"
        val recipes = RetrofitInstance.recipeService.searchRecipesByIngredients(
            ingredients = "tomato,cheese",
            apiKey = apiKey,
            number = 5
        )
        println("Search Results:")
        recipes.forEach { println(it) }

        // Get information for the first recipe found
        if (recipes.isNotEmpty()) {
            val recipeInfo = RetrofitInstance.recipeService.getRecipeInformation(
                id = recipes[0].id,
                apiKey = apiKey
            )
            println("\nRecipe Information:")
            println(recipeInfo)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
