package com.example.lemmecook_frontend.activities.plan

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lemmecook_frontend.activities.plan.ScheduleViewModel

@Composable
fun TestScreen() {
    Text("Test Screen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = viewModel()
) {
    Surface(color = Color.White)
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            TodayDate()

            CalendarTabs()

            MealSchedule(viewModel = viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayDate() {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")
    val formatted = today.format(formatter)

    Text(
        text = formatted,
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Black
    )

    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = Color.Gray
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTabs() {
    val currentDate = LocalDate.now()
    val days = (0..6).map { currentDate.plusDays(it.toLong()) }

    Surface(color = Color.White) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(days) { date ->
                CalendarTab(date = date)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTab(date: LocalDate) {
    val dayOfWeek = date.format(DateTimeFormatter.ofPattern("EEE"))
    val dayOfMonth = date.dayOfMonth

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .padding(8.dp)
    ) {
        Text(text = dayOfWeek, style = MaterialTheme.typography.labelMedium)
        Text(text = dayOfMonth.toString(), style = MaterialTheme.typography.labelMedium)
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = timeSlot.time,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (timeSlot.meal != null) {
                Column {
                    Text(text = timeSlot.meal.name, style = MaterialTheme.typography.labelMedium)
                    if (timeSlot.meal.description != null) {
                        Text(
                            text = timeSlot.meal.description,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            } else {
                Text(text = "No meal scheduled", style = MaterialTheme.typography.labelMedium)
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