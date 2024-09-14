package com.example.lemmecook_frontend.models.response

data class AuthResponse(
    val status: String,
    val userId: String?,
    val fullName: String?,
    val username: String?
)
