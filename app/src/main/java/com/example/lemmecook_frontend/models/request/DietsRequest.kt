package com.example.lemmecook_frontend.models.request

import com.google.gson.annotations.SerializedName

data class DietsRequest(
    @SerializedName("userId")
    val user_id: String,

    @SerializedName("diets")
    val diets: List<String>
)
