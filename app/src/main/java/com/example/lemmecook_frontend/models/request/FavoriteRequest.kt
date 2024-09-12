package com.example.lemmecook_frontend.models.request

data class FavoriteRequest (
    val user_id: Int,
    val meal_id: Int
)