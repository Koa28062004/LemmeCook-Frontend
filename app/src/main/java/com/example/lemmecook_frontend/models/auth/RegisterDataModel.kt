package com.example.lemmecook_frontend.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterDataModel(
    @SerializedName("username")
    var username: String = "",

    @SerializedName("email")
    var email: String = "",

    @SerializedName("password")
    var password: String = "",
)
