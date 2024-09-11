package com.example.lemmecook_frontend.activities.plan


data class Meal(val name: String, val description: String? = null)
data class TimeSlot(val time: String, val meal: Meal? = null)


class ScheduleViewModel : androidx.lifecycle.ViewModel() {
    val schedule: List<TimeSlot> = listOf(
        TimeSlot("8:00", Meal("Breakfast", "Pancakes")),
        TimeSlot("12:00", Meal("Lunch", "Salad")),
        TimeSlot("19:00", Meal("Dinner", "Pasta"))
    )
}