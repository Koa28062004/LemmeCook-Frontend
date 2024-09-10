package com.example.lemmecook_frontend.models.response

import com.example.lemmecook_frontend.models.health.ProgressDataModel

data class ProgressPostResponse (
    val status: String,
    val message: String
)

data class ProgressGetResponse (
    val status: String,
    val message: String? = null,
    val progress: ProgressDataModel? = null
)