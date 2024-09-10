package com.example.lemmecook_frontend.utilities

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTimeUtility {
    companion object {
        fun getCurrentDateAsString(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }
}