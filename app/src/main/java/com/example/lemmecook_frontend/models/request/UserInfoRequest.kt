package com.example.lemmecook_frontend.models.request

data class UserInfoRequest(
    val userId: String,
    val username: String,
    val fullName: String,
    val password: String,
    val newPassword: String
)
