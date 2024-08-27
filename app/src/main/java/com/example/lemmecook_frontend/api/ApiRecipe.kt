package com.example.lemmecook_frontend.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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

// Data class for recipe information
@Serializable
data class RecipeInformation(
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val veryHealthy: Boolean,
    val cheap: Boolean,
    val veryPopular: Boolean,
    val sustainable: Boolean,
    val lowFodmap: Boolean,
    val weightWatcherSmartPoints: Int,
    val gaps: String,
    val preparationMinutes: Int,
    val cookingMinutes: Int,
    val aggregateLikes: Int,
    val healthScore: Int,
    val creditsText: String,
    val license: String,
    val sourceName: String,
    val pricePerServing: Double,
    val extendedIngredients: List<ExtendedIngredient>,
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val image: String,
    val imageType: String,
    val summary: String,
    val cuisines: List<String>,
    val dishTypes: List<String>,
    val diets: List<String>,
    val occasions: List<String>,
    val instructions: String?,
    val analyzedInstructions: List<AnalyzedInstruction>,
    val originalId: Int?,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String
)

@Serializable
data class ExtendedIngredient(
    val id: Int,
    val aisle: String,
    val image: String,
    val consistency: String,
    val name: String,
    val nameClean: String,
    val original: String,
    val originalName: String,
    val amount: Double,
    val unit: String,
    val meta: List<String>,
    val measures: Measures
)

@Serializable
data class Measures(
    val us: MeasureUnit,
    val metric: MeasureUnit
)

@Serializable
data class MeasureUnit(
    val amount: Double,
    val unitShort: String,
    val unitLong: String
)

@Serializable
data class AnalyzedInstruction(
    val name: String,
    val steps: List<InstructionStep>
)

@Serializable
data class InstructionStep(
    val number: Int,
    val step: String
)

interface RecipeService {
    @GET("recipes/findByIngredients")
    suspend fun searchRecipesByIngredients(
        @Query("apiKey") apiKey: String,
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 10
    ): List<Recipe>

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Query("apiKey") apiKey: String,
        @Path("id") id: Int, //get id from searchRecipesByIngredients
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("addWinePairing") addWinePairing: Boolean = false,
        @Query("addTasteData") addTasteData: Boolean = false
    ): RecipeInformation
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
