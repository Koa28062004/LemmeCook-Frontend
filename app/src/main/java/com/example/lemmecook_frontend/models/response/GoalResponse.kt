package com.example.lemmecook_frontend.models.response

import com.example.lemmecook_frontend.models.health.GoalDataModel

data class GoalPostResponse (
    val status: String,
    val message: String
)

data class GoalGetResponse (
    val status: String,
    val message: String? = null,
    val goal: GoalDataModel? = null
)