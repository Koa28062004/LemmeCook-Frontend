package com.example.lemmecook_frontend.activities.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

data class Meal(
    val name: String,
    val id: Int,
    val description: String? = null,
    val ingredients: List<String> = emptyList()
)

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
        TimeSlot(
            "9:00",
            Meal("Breakfast", 640352, "Cranberry Apple Crisp", listOf("2 cups fresh cranberries", "1/2 stick unsalted butter, cut into cubes", "1 1/2 cups regular oats (not quick-cooking)")),
            LocalDate.now()
        ),
        TimeSlot(
            "9:00",
            Meal("Lunch", 641803, "Easy & Delish! ~ Apple Crumble", listOf("1 Zest of lemon", "Dash of ground cloves", "3/4 stick of butter")),
            LocalDate.now().plusDays(1)
        ),
        TimeSlot(
            "9:00",
            Meal("Dinner", 73420, "Apple Or Peach Strudel", listOf("Milk, Eggs, Other Dairy", "1 tsp cinnamon", "1 tsp baking powder")),
            LocalDate.now().plusDays(2)
        )
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

    fun getMealByID(mealID: Int): Meal? {
        return schedule.value
            .mapNotNull { it.meal }
            .find { it.id == mealID }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChecklist() {
        _checklistItems.value = schedule.value
            .filter { it.meal != null && it.date == selectedDate.value }
            .flatMap { it.meal!!.ingredients }
            .distinct()
            .mapIndexed { index, ingredient ->
                ChecklistItem(
                    id = index +
                            1, text = ingredient
                )
            }
    }

    fun updateChecklistItem(item: ChecklistItem) {
        _checklistItems.value = _checklistItems.value.map {
            if (it.id == item.id) item else it
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteTimeSlot(timeSlot: TimeSlot) {
        _schedule.value = _schedule.value.toMutableList().also { it.remove(timeSlot) }
        updateChecklist()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editTimeSlot(updatedTimeSlot: TimeSlot) {
        _schedule.value = _schedule.value.toMutableList().also {
            it[it.indexOf(updatedTimeSlot)] = updatedTimeSlot
        }
        updateChecklist()
    }

    init {
        _schedule.value = defaultSchedule
        updateChecklist()
    }
}