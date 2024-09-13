package com.example.lemmecook_frontend.singleton

import android.content.Context
import android.util.Log
import com.example.lemmecook_frontend.models.health.ProgressDataModel
import com.example.lemmecook_frontend.utilities.DateTimeUtility
import com.example.lemmecook_frontend.utilities.ProgressApiUtility

object ProgressSession {
    val date = DateTimeUtility.getCurrentDateAsString()
    var progress: ProgressDataModel = ProgressApiUtility.getDefaultProgress(date)

    fun fetchProgressData(context: Context) {
        val userId = UserSession.userId?.toInt() ?: -1
        if (userId == -1) {
            Log.e("ProgressSession", "User ID is not set")
            return
        }

        ProgressApiUtility.getProgress(
            userId = userId,
            date = date,
            context = context,
            onSuccess = { progressData ->
                ProgressSession.progress = progressData
                Log.d("ProgressSession", "Progress data fetched successfully: $progressData")
            },
            onError = { errorMessage ->
                Log.e("ProgressSession", "Error fetching goal data: $errorMessage")
            }
        )
    }
}