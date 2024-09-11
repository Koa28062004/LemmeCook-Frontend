package com.example.lemmecook_frontend.activities.plan


data class Meal(val name: String, val description: String? = null, val ingredients: List<String> = emptyList())
data class TimeSlot(val time: String, val meal: Meal? = null)
data class ChecklistItem(
    val id: Int = 0,
    val text: String,
    var isChecked: Boolean = false
)

class ScheduleViewModel : androidx.lifecycle.ViewModel() {
    val schedule: List<TimeSlot> = listOf(
        TimeSlot("8:00 AM", Meal("Breakfast", "Pancakes", listOf("Flour", "Eggs", "Milk"))),
        TimeSlot("12:00 PM", Meal("Lunch", "Salad", listOf("Lettuce", "Tomato", "Cucumber"))),
        TimeSlot("6:00 PM", Meal("Dinner", "Pasta", listOf("Pasta", "Tomato Sauce", "Cheese")))
    )
}