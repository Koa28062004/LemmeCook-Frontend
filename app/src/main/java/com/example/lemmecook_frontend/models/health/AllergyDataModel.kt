package com.example.lemmecook_frontend.models.health

import com.google.gson.annotations.SerializedName

data class AllergyDataModel(
    @SerializedName("allergy")
    var allergy: String = "",
)
