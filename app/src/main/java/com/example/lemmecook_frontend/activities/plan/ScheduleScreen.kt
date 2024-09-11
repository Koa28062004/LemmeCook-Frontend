package com.example.lemmecook_frontend.activities.plan

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
            Spacer(modifier = Modifier.height(8.dp))
            ButtonToDisplayMealOrChecklist()

            MealSchedule(viewModel = viewModel)
        }
    }
}

@Composable
fun ButtonToDisplayMealOrChecklist() {
    Surface(color = Color.White) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            CustomButton(text = "View Meal Schedule", onClick = {})

            Spacer(modifier = Modifier.width(16.dp))

            CustomButton(text = "View Checklist", onClick = {})
        }
    }
}

@Composable
private fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.bg_green),
            containerColor = colorResource(id = R.color.grey)
        )
    )
    {
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