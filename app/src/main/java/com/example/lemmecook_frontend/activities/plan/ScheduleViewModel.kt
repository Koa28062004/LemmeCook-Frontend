package com.example.lemmecook_frontend.activities.plan


data class Meal(val name: String, val description: String? = null)
data class TimeSlot(val time: String, val meal: Meal? = null)


class ScheduleViewModel : androidx.lifecycle.ViewModel() {
    val schedule: List<TimeSlot> = listOf(
        TimeSlot("8:00 AM", Meal("Breakfast", "Pancakes")),
        TimeSlot("12:00 PM", Meal("Lunch", "Salad")),
        TimeSlot("6:00 PM", Meal("Dinner", "Pasta"))
    )
}