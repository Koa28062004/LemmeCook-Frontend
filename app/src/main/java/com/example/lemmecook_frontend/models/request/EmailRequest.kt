package com.example.lemmecook_frontend.models.request

import com.google.gson.annotations.SerializedName

data class EmailRequest(
    @SerializedName("email") val textEmail: String
)
