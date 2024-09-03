package com.example.lemmecook_frontend.models.data

import com.google.gson.annotations.SerializedName

data class EmailRequest(
    @SerializedName("email") val textEmail: String
)
