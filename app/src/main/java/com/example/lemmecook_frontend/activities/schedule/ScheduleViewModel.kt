package com.example.lemmecook_frontend.activities.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

data class Meal(val name: String, val description: String? = null, val ingredients: List<String> = emptyList())
data class TimeSlot(val time: String, val meal: Meal? = null, val date: LocalDate)

data class ChecklistItem(
    val id: Int = 0,
    val text: String,
    var isChecked: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleViewModel : androidx.lifecycle.ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val defaultSchedule = listOf(
        TimeSlot("8:00 AM", Meal("Breakfast", "Pancakes", listOf("Flour", "Eggs", "Milk")), LocalDate.now()),
        TimeSlot("12:00 PM", Meal("Lunch", "Salad", listOf("Lettuce", "Tomato", "Cucumber")), LocalDate.now().plusDays(1)),
        TimeSlot("6:00 PM", Meal("Dinner", "Pasta", listOf("Pasta", "Tomato Sauce", "Cheese")), LocalDate.now().plusDays(2))
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _schedule = MutableStateFlow<List<TimeSlot>>(emptyList())
    val schedule: StateFlow<List<TimeSlot>> = _schedule.asStateFlow()

    private val _checklistItems = MutableStateFlow<List<ChecklistItem>>(emptyList())
    val checklistItems: StateFlow<List<ChecklistItem>> = _checklistItems.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
        updateChecklist()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChecklist() {
        _checklistItems.value = schedule.value
            .filter { it.meal != null && it.date == selectedDate.value } // Filter by selected date
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

    init {
        _schedule.value = defaultSchedule
        updateChecklist()
    }
}