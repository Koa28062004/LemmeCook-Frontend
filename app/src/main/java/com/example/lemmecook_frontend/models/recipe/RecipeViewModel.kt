package com.example.lemmecook_frontend.models.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipeViewModel : ViewModel() {
    private val _recipeInformation = MutableLiveData<RecipeInformation>()
    val recipeInformation: LiveData<RecipeInformation> = _recipeInformation

    fun setRecipeInformation(recipe: RecipeInformation) {
        _recipeInformation.value = recipe
    }
}
