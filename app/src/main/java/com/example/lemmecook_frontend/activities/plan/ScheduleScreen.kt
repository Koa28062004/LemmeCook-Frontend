package com.example.lemmecook_frontend.activities.plan

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lemmecook_frontend.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TestScreen() {
    Text("Test Screen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = viewModel()
) {
    var showMealSchedule by remember { mutableStateOf(true) }

    Surface(color = Color.White)
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {
            TodayDate()

            CalendarTabs()

            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))

            ButtonToDisplayMealOrChecklist(
                showMealSchedule = showMealSchedule,
                onToggleView = { showMealSchedule = !showMealSchedule }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (showMealSchedule) {
                MealSchedule(viewModel = viewModel)
            } else {
                ChecklistContent(viewModel = viewModel)
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun ChecklistContent(
    viewModel: ScheduleViewModel
) {
    val checklistItems = remember { mutableStateListOf<ChecklistItem>() }

    val ingredients = viewModel.schedule
        .filter { it.meal != null } // Filter out timeslots without meals
        .flatMap { it.meal!!.ingredients } // Get all ingredients
        .distinct() // Remove duplicates
        .mapIndexed { index, ingredient ->
            ChecklistItem(id = index + 1, text = ingredient)
        }
    checklistItems.clear()
    checklistItems.addAll(ingredients)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(checklistItems) { item ->
            ChecklistItemRow(item) { isChecked ->
                val index = checklistItems.indexOfFirst { it.id == item.id }
                if (index != -1) {
                    checklistItems[index] = item.copy(isChecked = isChecked)
                }
            }
        }
    }
}

@Composable
fun ChecklistItemRow(item: ChecklistItem, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!item.isChecked) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = { isChecked -> onCheckedChange(isChecked) }
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item.text,
            style = TextStyle.Default,
            color = if (item.isChecked) {
                colorResource(id = R.color.gr_text).copy(alpha = 0.5f) // Faded color if checked
            } else {
                colorResource(id = R.color.gr_text) // Normal color if not checked
            }
        )
    }
}

@Composable
fun ButtonToDisplayMealOrChecklist(showMealSchedule: Boolean, onToggleView: () -> Unit) {
    Surface(color = Color.White) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth() // Make buttons occupy full width
        ) {
            CustomButton(
                text = "View Meal Schedule",
                onClick = onToggleView, // Toggle view on click
                isSelected = showMealSchedule // Highlight the active button
            )

            Spacer(modifier = Modifier.width(16.dp))

            CustomButton(
                text = "View Checklist",
                onClick = onToggleView,
                isSelected = !showMealSchedule // Highlight the active button
            )
        }
    }
}

@Composable
private fun CustomButton(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.bg_green),
            containerColor = if (isSelected) colorResource(id = R.color.grey) else Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
    ) {
        Text(text)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayDate() {
    val today = LocalDate.now()
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE")
    val dayOfMonthFormatter = DateTimeFormatter.ofPattern("d")
    val dateFormatter = DateTimeFormatter.ofPattern("MMMM d")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = today.format(dayOfMonthFormatter),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = today.format(dayOfWeekFormatter), // Day of the week
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.gr_text)
            )

            Text(
                text = today.format(dateFormatter), // Month and day
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.gr_text)
            )

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
    HorizontalDivider(color = Color.LightGray)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTabs() {
    val currentDate = LocalDate.now()
    val days = (0..6).map { currentDate.plusDays(it.toLong()) }

    Surface(color = Color.White) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(days) { date ->
                CalendarTab(date = date) {
                    Log.d("CalendarTab", "Date clicked: $it")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTab(date: LocalDate, onDateClick: (LocalDate) -> Unit) {
    var isSelected by remember { mutableStateOf(false) }

    Button(
        onClick = {
            onDateClick(date)
            isSelected = !isSelected
        },
        modifier = Modifier
            .width(IntrinsicSize.Min),
        colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) colorResource(id = R.color.bg_green) else Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val dayOfWeek = date.format(DateTimeFormatter.ofPattern("EEE"))
            val dayOfMonth = date.dayOfMonth

            Text(
                text = dayOfWeek, style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) Color.White else colorResource(id = R.color.gr_text)
            )
            Text(
                text = dayOfMonth.toString(), style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}

@Composable
fun MealSchedule(viewModel: ScheduleViewModel) {
    Column {
        viewModel.schedule.forEach { timeSlot ->
            MealCard(timeSlot = timeSlot)
        }
    }
}

@Composable
fun MealCard(timeSlot: TimeSlot) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    )
    {
        //Hour
        Text(
            text = timeSlot.time,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        //Meal
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.bg_green)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                if (timeSlot.meal != null) {
                    Column {
                        Text(
                            text = timeSlot.meal.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                        )
                        if (timeSlot.meal.description != null) {
                            Text(
                                text = timeSlot.meal.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                            )
                        }
                    }
                } else {
                    Text(
                        text = "No meal scheduled",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ScheduleScreenPreview() {
    ScheduleScreen()
}