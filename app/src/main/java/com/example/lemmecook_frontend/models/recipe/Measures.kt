package com.example.lemmecook_frontend.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class Measures(
    val us: MeasureUnit,
    val metric: MeasureUnit
)