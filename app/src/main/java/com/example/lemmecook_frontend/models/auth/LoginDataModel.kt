package com.example.lemmecook_frontend.models.auth

import com.google.gson.annotations.SerializedName

data class LoginDataModel(
    @SerializedName("email")
    var email: String = "",

    @SerializedName("password")
    var password: String = "",
)
