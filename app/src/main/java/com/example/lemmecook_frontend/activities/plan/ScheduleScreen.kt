package com.example.lemmecook_frontend.activities.plan

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MealPlanScheduleScreen() {
    val today = LocalDate.now()
    val calendarDays = remember { mutableStateListOf<LocalDate>() }
    val scrollState = rememberScrollState()
    val mealData = remember { mutableStateListOf<MealData>() }
    val currentDay = remember { mutableStateOf(today) }
    val mealTimes = remember { mutableStateListOf<String>() }

    LaunchedEffect(key1 = Unit) {
        // Populate the calendar days
        calendarDays.clear()
        for (i in 0..6) {
            calendarDays.add(today.plusDays(i.toLong()))
        }
    }

    LaunchedEffect(key1 = Unit) {
        // Populate meal times (replace with actual data from your source)
        mealTimes.addAll(
            listOf(
                "7:00 AM",
                "9:00 AM",
                "12:00 PM",
                "3:00 PM",
                "6:00 PM",
                "9:00 PM"
            )
        )

        // Populate meal data (replace with actual data from your source)
        mealData.addAll(
            listOf(
                MealData("Breakfast", "Oatmeal with berries", calendarDays[0], "7:00 AM"),
                MealData("Lunch", "Sandwich", calendarDays[0], "12:00 PM"),
                MealData("Dinner", "Chicken with vegetables", calendarDays[0], "6:00 PM"),
                MealData("Snack", "Fruit", calendarDays[1], "9:00 AM"),
                MealData("Lunch", "Salad", calendarDays[1], "12:00 PM"),
                MealData("Dinner", "Pasta", calendarDays[1], "6:00 PM"),
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meal Plan") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Handle navigation back
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Handle navigation forward
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            // Calendar Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(bottom = 8.dp)
            ) {
                calendarDays.forEach { day ->
                    CalendarDay(day)
                }
            }

            // Timeline
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                mealTimes.forEach { time ->
                    TimelineRow(time, mealData, currentDay.value)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDay(day: LocalDate) {
    val formatter = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.getDefault())
    val formattedDay = day.format(formatter)

    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(4.dp)
            .background(Color.White)
            .padding(8.dp)
    ) {
        Text(
            text = formattedDay,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        if (day == LocalDate.now()) {
            Text(
                text = "Today",
                fontWeight = FontWeight.Light,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun TimelineRow(time: String, mealData: List<MealData>, currentDay: LocalDate) {
    val currentMeal = mealData.find { it.time == time && it.day == currentDay }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time Column
        Column(
            modifier = Modifier
                .width(60.dp)
                .padding(start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = time,
                color = Color.Black
            )
        }

        // Meal Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
        ) {
            if (currentMeal != null) {
                Text(
                    text = "${currentMeal.type}: ${currentMeal.name}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            } else {
                // Add a placeholder for empty meals
                Text(
                    text = " ",
                    fontWeight = FontWeight.Light,
                    color = Color.LightGray
                )
            }
        }
    }
}

data class MealData(
    val type: String,
    val name: String,
    val day: LocalDate,
    val time: String
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealPlanScheduleScreen()
}