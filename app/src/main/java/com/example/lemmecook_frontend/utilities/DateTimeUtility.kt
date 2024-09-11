package com.example.lemmecook_frontend.utilities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateTimeUtility {
    companion object {
        fun getCurrentDateAsString(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateFormat.format(Date())
        }

        fun showDateTimePicker(context: Context, onDateTimePicked: (String) -> Unit) {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(context, {_, year, month, dayOfMonth ->
                val selectedDate = "$year-${month+1}-$dayOfMonth"

                val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                val currentMinute = calendar.get(Calendar.MINUTE)

                TimePickerDialog(context, {_, hourOfDay, minute ->
                    val selectedTime = "$hourOfDay:$minute"
                    val selectedDateTime = "$selectedDate $selectedTime"

                    onDateTimePicked(selectedDateTime)
                }, currentHour, currentMinute, true).show()
            }, currentYear, currentMonth, currentDay).show()
        }
    }
}