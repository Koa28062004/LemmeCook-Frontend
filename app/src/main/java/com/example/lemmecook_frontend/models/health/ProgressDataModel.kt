package com.example.lemmecook_frontend.models.health

data class ProgressDataModel (
    val user_id: Int,
    val date: String,
    val calories: Float,
    val fat: Float,
    val protein: Float,
    val carb: Float
)