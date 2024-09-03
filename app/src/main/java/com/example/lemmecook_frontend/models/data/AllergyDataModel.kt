package com.example.lemmecook_frontend.models.data

import com.google.gson.annotations.SerializedName

data class AllergyDataModel(
    @SerializedName("allergy")
    var allergy: String = "",
)
