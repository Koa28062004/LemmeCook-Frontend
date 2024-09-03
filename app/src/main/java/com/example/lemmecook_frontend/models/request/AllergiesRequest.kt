package com.example.lemmecook_frontend.models.request

import com.google.gson.annotations.SerializedName

data class AllergiesRequest(
    @SerializedName("userId")
    val user_id: String,

    @SerializedName("allergies")
    val allergies: List<String>
)
