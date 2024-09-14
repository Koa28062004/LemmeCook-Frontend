package com.example.lemmecook_frontend.models.request

import com.google.gson.annotations.SerializedName

data class FavoriteRequest (
    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("meal_id")
    val meal_id: Int
)