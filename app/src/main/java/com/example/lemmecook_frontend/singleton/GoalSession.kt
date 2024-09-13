package com.example.lemmecook_frontend.singleton

import com.example.lemmecook_frontend.models.health.GoalDataModel
import com.example.lemmecook_frontend.utilities.GoalApiUtility

object GoalSession {
    var goal: GoalDataModel = GoalApiUtility.getDefaultGoal()
}