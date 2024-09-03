package com.example.lemmecook_frontend.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class Nutrition(
    val nutrients: List<Nutrient>
)