package com.example.lemmecook_frontend.models.data

import com.google.gson.annotations.SerializedName

data class LoginDataModel(
    @SerializedName("email")
    var email: String = "",

    @SerializedName("password")
    var password: String = "",
)
