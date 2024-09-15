package com.example.lemmecook_frontend.activities.schedule

import android.os.Build
import android.util.Log
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
            "12:00",
            Meal(
                "Lunch",
                649985,
                "Light and Chunky Chicken Soup",
                listOf(
                    "1 cup of basmati rice",
                    "1 cup Chopped carrot",
                    "1 stick of Celery finelly chopped"
                )
            ),
            LocalDate.now()
        ),
        TimeSlot(
            "12:00",
            Meal(
                "Lunch",
                641803,
                "Easy & Delish! ~ Apple Crumble",
                listOf("1 Zest of lemon", "Dash of ground cloves", "3/4 stick of butter")
            ),
            LocalDate.now().plusDays(1)
        ),
        TimeSlot(
            "18:00",
            Meal(
                "Dinner",
                73420,
                "Apple Or Peach Strudel",
                listOf("Milk, Eggs, Other Dairy", "1 tsp cinnamon", "1 tsp baking powder")
            ),
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
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun updateChecklist() {
//        _checklistItems.value = schedule.value
//            .filter { it.meal != null && it.date == selectedDate.value }
//            .flatMap { it.meal!!.ingredients }
//            .distinct()
//            .mapIndexed { index, ingredient ->
//                ChecklistItem(
//                    id = index +
//                            1, text = ingredient
//                )
//            }
//    }

//    fun updateChecklistItem(item: ChecklistItem) {
//        _checklistItems.value = _checklistItems.value.map {
//            if (it.id == item.id) item else it
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteTimeSlot(timeSlot: TimeSlot) {
        _schedule.value = _schedule.value.toMutableList().also { it.remove(timeSlot) }
        updateChecklist()
    }

    //    @RequiresApi(Build.VERSION_CODES.O)
//    fun editTimeSlot(updatedTimeSlot: TimeSlot) {
//        _schedule.value = _schedule.value.toMutableList().also {
//            it[it.indexOf(updatedTimeSlot)] = updatedTimeSlot
//        }
//        updateChecklist()
//    }


    fun updateChecklistItem(item: ChecklistItem) {
        Log.d("Linh ChecklistApp", "Updating Checklist Item: $item") // Log before updating
        _checklistItems.value = _checklistItems.value.map {
            if (it.id == item.id) item else it
        }
        Log.d(
            "Linh ChecklistApp",
            "Checklist updated: ${_checklistItems.value}"
        ) // Log after updating
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChecklist() {
        val filteredSchedule = schedule.value
            .filter { it.meal != null && it.date == selectedDate.value }
        Log.d("Linh updateChecklist", "Filtered schedule items: $filteredSchedule")

        val allIngredients = filteredSchedule
            .flatMap { it.meal!!.ingredients }
            .distinct()
        Log.d("Linh updateChecklist", "Unique ingredients: $allIngredients")

        _checklistItems.value = allIngredients
            .mapIndexed { index, ingredient ->
                ChecklistItem(
                    id = index + 1,
                    text = ingredient
                )
            }
        Log.d("Linh updateChecklist", "Updated checklist: ${_checklistItems.value}")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun editTimeSlot(updatedTimeSlot: TimeSlot) {
        // 1. Find the index of the TimeSlot to update using the identifier:
        val index = _schedule.value.indexOfFirst {
            // Adjust the condition based on your identifier:
            val identifier = Pair(updatedTimeSlot.date, updatedTimeSlot.meal?.id)
            Log.d("Linh ScheduleViewModel", "Checking TimeSlot: date=${it.date}, MealId=${it.meal?.id}")
            Log.d("Linh ScheduleViewModel", "Identifier: $identifier")
            when (identifier) {
                is Pair<*, *> -> (it.date == identifier.first &&
                        it.meal?.id == identifier.second)  // If identifier is a Triple
                else -> false // Handle other identifier types if needed
            }
        }

        // 2. Update the schedule:
        if (index != -1) {
            Log.d("Linh ScheduleViewModel", "Found matching TimeSlot at index: $index")
            val updatedSchedule = _schedule.value.toMutableList()
            updatedSchedule[index] = updatedTimeSlot
            _schedule.value = updatedSchedule
            updateChecklist()
        } else {
            // Handle the case where the TimeSlot doesn't exist
            Log.e("Linh ScheduleViewModel", "Cannot edit TimeSlot: TimeSlot not found")
        }
    }

    init {
        _schedule.value = defaultSchedule
        updateChecklist()
    }
}