package com.example.lemmecook_frontend.activities.blog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider

class BlogViewModel : ViewModel() {
    fun launchBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        Log.d("BlogViewModel", "Launching browser with URL: $url")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Log.d("BlogViewModel", "Starting activity")
        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
        Log.d("BlogViewModel", "launchBrowser: Application Context: $applicationContext")
        applicationContext.startActivity(intent)
        Log.d("BlogViewModel", "Started activity")
    }
}