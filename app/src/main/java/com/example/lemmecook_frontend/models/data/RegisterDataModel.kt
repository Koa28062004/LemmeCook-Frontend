package com.example.lemmecook_frontend.models.data

import com.google.gson.annotations.SerializedName

data class RegisterDataModel(
    @SerializedName("username")
    var username: String = "",

    @SerializedName("email")
    var email: String = "",

    @SerializedName("password")
    var password: String = "",

    @SerializedName("fullName")
    var fullName: String = "",
)