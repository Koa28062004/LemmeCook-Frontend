package com.example.lemmecook_frontend.models.data

import com.google.gson.annotations.SerializedName

data class DietDataModel(
    @SerializedName("diet")
    var diet: String = "",
)
