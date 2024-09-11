package com.example.lemmecook_frontend.models.response

data class FavoritesPostResponse (
    val status: String,
    val message: String
)

data class FavoritesGetResponse (
    val status: String,
    val message: String? = null,
    val favorites: List<Int>? = null
)