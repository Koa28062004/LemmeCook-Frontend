package com.example.lemmecook_frontend.activities.schedule

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


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

    private val _checklistItems = MutableStateFlow<List<ChecklistItem>>(emptyList())
    val checklistItems: StateFlow<List<ChecklistItem>> = _checklistItems.asStateFlow()

    init {
        updateSchedule(schedule)
    }

    private fun updateSchedule(newSchedule: List<TimeSlot>) {
        _checklistItems.value = newSchedule
            .filter { it.meal != null }
            .flatMap { it.meal!!.ingredients }
            .distinct()
            .mapIndexed { index, ingredient ->
                ChecklistItem(id = index + 1, text = ingredient)
            }
    }

    fun updateChecklistItem(item: ChecklistItem) {
        _checklistItems.value = _checklistItems.value.map {
            if (it.id == item.id) item else it
        }
    }
}