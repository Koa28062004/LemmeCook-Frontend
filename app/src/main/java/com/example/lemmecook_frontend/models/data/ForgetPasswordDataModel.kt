package com.example.lemmecook_frontend.models.data

import com.google.gson.annotations.SerializedName
data class ForgetPasswordDataModel(
    @SerializedName("email")
    var email: String = "",

    @SerializedName("newPassword")
    var newPassword: String = "",
)
