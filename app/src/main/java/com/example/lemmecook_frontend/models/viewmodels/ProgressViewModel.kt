package com.example.lemmecook_frontend.models.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.lemmecook_frontend.models.health.ProgressDataModel
import com.example.lemmecook_frontend.utilities.DateTimeUtility
import com.example.lemmecook_frontend.utilities.ProgressApiUtility

class ProgressViewModel : ViewModel() {

    // LiveData to hold progress information
    private val _progress = MutableLiveData<ProgressDataModel?>()
    val progress: LiveData<ProgressDataModel?> get() = _progress

    // Function to fetch progress from API
    fun fetchProgress(userId: Int, context: Context) {
        viewModelScope.launch {
            ProgressApiUtility.getProgress(
                userId = userId,
                date = DateTimeUtility.getCurrentDateAsString(),
                context = context,
                onError = { error ->
                    Toast.makeText(context, "ProgressViewModel: $error", Toast.LENGTH_SHORT).show()
                },
                onSuccess = { progressData ->
                    _progress.value = progressData
                }
            )
        }
    }

    // Function to update progress (after recipe completion)
    fun updateProgress(updatedProgress: ProgressDataModel, context: Context) {
        viewModelScope.launch {
            ProgressApiUtility.setProgress(
                progress = updatedProgress,
                context = context,
            )
            _progress.value = updatedProgress
        }
    }
}
