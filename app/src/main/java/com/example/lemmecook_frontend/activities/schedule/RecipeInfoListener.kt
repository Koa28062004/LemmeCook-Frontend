package com.example.lemmecook_frontend.activities.schedule

import com.example.lemmecook_frontend.models.recipe.RecipeInformation

interface RecipeInfoListener {
    fun onRecipeInformationUpdated(recipeInformation: RecipeInformation)
}