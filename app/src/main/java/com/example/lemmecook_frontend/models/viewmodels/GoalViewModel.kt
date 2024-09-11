package com.example.lemmecook_frontend.models.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemmecook_frontend.models.health.GoalDataModel
import com.example.lemmecook_frontend.utilities.GoalApiUtility
import kotlinx.coroutines.launch

class GoalViewModel : ViewModel() {
    private val _goal: MutableState<GoalDataModel?> = mutableStateOf(null)
    val goal: MutableState<GoalDataModel?> get() = _goal

    fun fetchGoals(userId: Int, context: Context) {
        viewModelScope.launch {
            GoalApiUtility.getGoals(
                userId = userId,
                context = context,
                onError = { error ->
                    Toast.makeText(context, "GoalViewModel: $error", Toast.LENGTH_SHORT).show()
                },
                onSuccess = { fetchedGoal ->
                    _goal.value = fetchedGoal
                }
            )
        }
    }

    fun updateGoal(updatedGoal: GoalDataModel, context: Context) {
        viewModelScope.launch {
            GoalApiUtility.setGoal(
                goal = updatedGoal,
                context = context
            )
        }
    }
}
