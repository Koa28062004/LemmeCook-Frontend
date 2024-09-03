package com.example.lemmecook_frontend.models.auth

import com.google.gson.annotations.SerializedName

data class ProfileDataModel(
    @SerializedName("fullName")
    var fullName: String = "",

    @SerializedName("language")
    var language: String = "Vie",

    @SerializedName("avatar_link")
    var avatar_link : String = "",
)