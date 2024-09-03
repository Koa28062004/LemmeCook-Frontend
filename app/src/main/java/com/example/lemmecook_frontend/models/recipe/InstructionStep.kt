package com.example.lemmecook_frontend.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class InstructionStep(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>
)