package com.example.lemmecook_frontend.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val id: Int,
    val name: String,
)