package com.example.lemmecook_frontend.models.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemmecook_frontend.api.RecipeService
import com.example.lemmecook_frontend.models.recipe.RecipeInformation
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecipeViewModel : ViewModel() {
    private val apiKey = BuildConfig.SPOON_API_KEY
    private val _recipeInformation = MutableLiveData<RecipeInformation>()
    val recipeInformation: LiveData<RecipeInformation> = _recipeInformation

    fun setRecipeInformation(recipe: RecipeInformation) {
        _recipeInformation.value = recipe
    }

    fun fetchRecipeFromAPI(recipeID: Int) {
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.spoonacular.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val api: RecipeService = retrofit.create(RecipeService::class.java)

                // Fetch the recipe information
                val recipeInfo = api.getRecipeInformation(apiKey, recipeID)
                _recipeInformation.postValue(recipeInfo)

            } catch (e: Exception) {
                // Handle any errors
                Log.e("RecipeViewModel", "Error fetching recipe information: ${e.message}")
            }
        }
    }
}
