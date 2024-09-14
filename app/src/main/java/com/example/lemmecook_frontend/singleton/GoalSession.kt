package com.example.lemmecook_frontend.singleton

import android.content.Context
import android.util.Log
import com.example.lemmecook_frontend.models.health.GoalDataModel
import com.example.lemmecook_frontend.utilities.GoalApiUtility

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object GoalSession {
    private val _goal = MutableStateFlow(GoalApiUtility.getDefaultGoal())
    val goal: StateFlow<GoalDataModel> = _goal

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
                _goal.value = goalData
                Log.d("GoalSession", "Goal data fetched successfully: $goalData")
            },
            onError = { errorMessage ->
                Log.e("GoalSession", "Error fetching goal data: $errorMessage")
            }
        )
    }

    fun updateGoal(context: Context, goalData: GoalDataModel) {
        val userId = UserSession.userId?.toInt() ?: -1
        if (userId == -1) {
            Log.e("GoalSession", "User ID is not set")
            return
        }

        val updatedGoalData = goalData.copy(user_id = userId)

        GoalApiUtility.setGoal(
            context = context,
            goal = updatedGoalData,
            onSuccess = { updatedGoal ->
                _goal.value = updatedGoal
                Log.d("GoalSession", "Goal updated successfully: $updatedGoal")
            },
            onError = { errorMessage ->
                Log.e("GoalSession", "Error updating goal data: $errorMessage")
            }
        )
    }
}
