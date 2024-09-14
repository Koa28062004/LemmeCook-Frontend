package com.example.lemmecook_frontend.utilities

import android.content.Context
import android.widget.Toast
import com.example.lemmecook_frontend.api.GoalApi
import com.example.lemmecook_frontend.models.health.GoalDataModel
import com.example.lemmecook_frontend.models.response.GoalGetResponse
import com.example.lemmecook_frontend.models.response.GoalPostResponse
import com.example.lemmecook_frontend.singleton.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalApiUtility {
    companion object {
        // Function to get default goal
        fun getDefaultGoal(): GoalDataModel {
            return GoalDataModel(
                user_id = UserSession.userId?.toInt() ?: -1,
                calories = 1000f,
                fat = 1000f,
                protein = 1000f,
                carb = 1000f
            )
        }

        // Function to set a goal
        fun setGoal(goal: GoalDataModel,
                    context: Context,
                    onSuccess: (GoalDataModel) -> Unit,
                    onError: (String) -> Unit) {
            val goalApi = ApiUtility.getApiClient().create(GoalApi::class.java)

            goalApi.updateUserGoaL(goal).enqueue(object : Callback<GoalPostResponse> {
                override fun onResponse(call: Call<GoalPostResponse>, response: Response<GoalPostResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Goal set successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to set goal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GoalPostResponse>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Function to get user's goals
        fun getGoals(userId: Int, context: Context, onSuccess: (GoalDataModel) -> Unit, onError: (String) -> Unit) {
            val goalApi = ApiUtility.getApiClient().create(GoalApi::class.java)

            goalApi.getUserGoals(userId).enqueue(object : Callback<GoalGetResponse> {
                override fun onResponse(call: Call<GoalGetResponse>, response: Response<GoalGetResponse>) {
                    if (response.isSuccessful) {
                        val goalResponse = response.body()
                        if (goalResponse != null && goalResponse.status == "success") {
                            val goal = goalResponse.goal ?: getDefaultGoal()
                            onSuccess(goal)
                        } else {
                            onError(goalResponse?.message ?: "Failed to get goals")
                        }
                    } else {
                        onError("Failed to get goals")
                    }
                }

                override fun onFailure(call: Call<GoalGetResponse>, t: Throwable) {
                    onError(t.message ?: "An unknown error occurred")
                }
            })
        }
    }
}