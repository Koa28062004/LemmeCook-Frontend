package com.example.lemmecook_frontend.utilities

import android.content.Context
import android.widget.Toast
import com.example.lemmecook_frontend.api.ProgressApi
import com.example.lemmecook_frontend.models.health.ProgressDataModel
import com.example.lemmecook_frontend.models.response.ProgressGetResponse
import com.example.lemmecook_frontend.models.response.ProgressPostResponse
import com.example.lemmecook_frontend.singleton.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgressApiUtility {
    companion object {
        // Function to update or set today's progress
        fun setProgress(progress: ProgressDataModel, context: Context) {
            val progressApi = ApiUtility.getApiClient().create(ProgressApi::class.java)

            progressApi.updateTodayProgress(progress).enqueue(object : Callback<ProgressPostResponse> {
                override fun onResponse(call: Call<ProgressPostResponse>, response: Response<ProgressPostResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Progress updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to update progress", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProgressPostResponse>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Function to get user's progress for a specific date
        fun getProgress(userId: Int, date: String, context: Context, onSuccess: (ProgressDataModel) -> Unit, onError: (String) -> Unit) {
            val progressApi = ApiUtility.getApiClient().create(ProgressApi::class.java)

            progressApi.getTodayProgress(userId, date).enqueue(object : Callback<ProgressGetResponse> {
                override fun onResponse(call: Call<ProgressGetResponse>, response: Response<ProgressGetResponse>) {
                    if (response.isSuccessful) {
                        val progressResponse = response.body()
                        if (progressResponse != null && progressResponse.status == "success") {
                            val progress = progressResponse.progress ?: ProgressDataModel(
                                user_id = UserSession.userId?.toInt() ?: -1,
                                date = date,
                                calories = 100f,
                                fat = 100f,
                                protein = 100f,
                                carb = 100f
                            )
                            onSuccess(progress)
                        } else {
                            onError(progressResponse?.message ?: "Failed to retrieve progress")
                        }
                    } else {
                        onError("Failed to retrieve progress")
                    }
                }

                override fun onFailure(call: Call<ProgressGetResponse>, t: Throwable) {
                    onError(t.message ?: "An unknown error occurred")
                }
            })
        }
    }
}