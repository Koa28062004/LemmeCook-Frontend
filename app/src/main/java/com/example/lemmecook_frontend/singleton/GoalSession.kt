package com.example.lemmecook_frontend.singleton

import android.content.Context
import android.util.Log
import com.example.lemmecook_frontend.models.health.GoalDataModel
import com.example.lemmecook_frontend.utilities.GoalApiUtility

object GoalSession {
    var goal: GoalDataModel = GoalApiUtility.getDefaultGoal()

    fun fetchGoalData(context: Context) {
        val userId = UserSession.userId?.toInt() ?: -1
        if (userId == -1) {
            Log.e("GoalSession", "User ID is not set")
            return
        }

        GoalApiUtility.getGoals(
            userId = userId,
            context = context,
            onSuccess = { goalData ->
                goal = goalData
                Log.d("GoalSession", "Goal data fetched successfully: $goalData")
            },
            onError = { errorMessage ->
                Log.e("GoalSession", "Error fetching goal data: $errorMessage")
            }
        )
    }
}
