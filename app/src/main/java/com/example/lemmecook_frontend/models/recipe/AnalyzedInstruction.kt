package com.example.lemmecook_frontend.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedInstruction(
    val name: String,
    val steps: List<InstructionStep>
)