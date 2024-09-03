package com.example.lemmecook_frontend.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class MeasureUnit(
    val amount: Double,
    val unitShort: String,
    val unitLong: String
)