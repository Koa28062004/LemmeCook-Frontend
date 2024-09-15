package com.example.lemmecook_frontend.activities.schedule

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.activities.NavHost.RecipeOverviewScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    navController: NavHostController,
    viewModel: ScheduleViewModel = viewModel(),
) {
    var showMealSchedule by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Surface(color = Color.White)
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {
            Spacer(modifier = Modifier.height(16.dp))
            TodayDate()

            Spacer(modifier = Modifier.height(8.dp))

            CalendarTabs(selectedDate = selectedDate, onDateSelected = { newDate ->
                selectedDate = newDate
                viewModel.updateSelectedDate(newDate)
            })

            Spacer(modifier = Modifier.height(16.dp))

            ButtonToDisplayMealOrChecklist(
                showMealSchedule = showMealSchedule,
                onToggleView = { showMealSchedule = !showMealSchedule }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (showMealSchedule) {
                MealSchedule(
                    viewModel = viewModel,
                    selectedDate = selectedDate,
                    navController = navController
                )
            } else {
                ChecklistContent(viewModel = viewModel, onCheckedChange = { updatedItem ->
                    viewModel.updateChecklistItem(updatedItem)
                })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
fun ChecklistContent(
    viewModel: ScheduleViewModel,
    onCheckedChange: (ChecklistItem) -> Unit
) {
    val checklistItems by viewModel.checklistItems.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(checklistItems) { item ->
            ChecklistItemRow(item = item, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
fun ChecklistItemRow(item: ChecklistItem, onCheckedChange: (ChecklistItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(item.copy(isChecked = !item.isChecked)) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = { isChecked ->
                onCheckedChange(item.copy(isChecked = isChecked))
            },
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.bg_green),
            )
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item.text,
            style = TextStyle.Default,
            color = if (item.isChecked) {
                colorResource(id = R.color.gr_text).copy(alpha = 0.5f)
            } else {
                colorResource(id = R.color.gr_text)
            }
        )
    }
}

@Composable
fun ButtonToDisplayMealOrChecklist(showMealSchedule: Boolean, onToggleView: () -> Unit) {
    Surface(color = Color.White) {
        if (showMealSchedule) {
            CustomButton(
                text = "View Checklist of Ingredients",
                onClick = onToggleView
            )
        } else {
            CustomButton(
                text = "View Meal Schedule",
                onClick = onToggleView
            )
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
        ),
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 16.dp, end = 16.dp)
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
            style = MaterialTheme.typography.displayMedium,
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTabs(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(7) { index ->
            val date = LocalDate.now().plusDays(index.toLong())
            CalendarTab(
                date = date,
                isSelected = date == selectedDate,
                onDateClick = onDateSelected
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTab(date: LocalDate, isSelected: Boolean, onDateClick: (LocalDate) -> Unit) {
    Button(
        onClick = { onDateClick(date) },
        modifier = Modifier
            .wrapContentWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) colorResource(id = R.color.bg_green) else Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
        ) {
            val dayOfWeek = date.format(DateTimeFormatter.ofPattern("EEE"))
            val dayOfMonth = date.dayOfMonth

            Text(
                text = dayOfWeek, style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) Color.White else colorResource(id = R.color.gr_text)
            )
            Text(
                text = dayOfMonth.toString(), style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MealSchedule(
    viewModel: ScheduleViewModel,
    selectedDate: LocalDate,
    navController: NavHostController
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var timeSlotToEdit by remember { mutableStateOf(TimeSlot("", date = selectedDate)) }

    LazyColumn {
        items(viewModel.schedule.value.filter { it.date == selectedDate }) { timeSlot ->
            MealCard(
                timeSlot = timeSlot,
                navController = navController,
                onDelete = { viewModel.deleteTimeSlot(it) },
                onEdit = {
                    timeSlotToEdit = it
                    showEditDialog = true
                }
            )
        }
    }

    if (showEditDialog) {
        EditTimeSlotDialog(
            timeSlot = timeSlotToEdit,
            viewModel = viewModel,
            onConfirm = { updatedTimeSlot ->
                viewModel.editTimeSlot(updatedTimeSlot)
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }
}

@Composable
fun MealCard(
    timeSlot: TimeSlot,
    navController: NavHostController,
    onDelete: (TimeSlot) -> Unit,
    onEdit: (TimeSlot) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    )
    {
        //Hour
        Box(
            modifier = Modifier
                .padding(8.dp)
                .width(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = timeSlot.time,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Button(
                onClick = { onDelete(timeSlot) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = colorResource(id = R.color.bg_green)
                ),
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text("Del")
            }

            Button(
                onClick = { onEdit(timeSlot) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = colorResource(id = R.color.bg_green)
                ),
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text("Edit")
            }
        }
        //Meal
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.bg_green)
            ),
            onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "recipeId",
                    timeSlot.meal?.id ?: 649985
                )
                navController.navigateTo(RecipeOverviewScreen.route)
            },
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
                            style = MaterialTheme.typography.bodyLarge,
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
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTimeSlotDialog(
    viewModel: ScheduleViewModel,
    timeSlot: TimeSlot,
    onConfirm: (TimeSlot) -> Unit,
    onDismiss: () -> Unit
) {
    var newTime by remember { mutableStateOf(timeSlot.time) }
    var newMealId by remember { mutableIntStateOf(timeSlot.meal?.id ?: 0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Time Slot") },
        text = {
            Column {
                OutlinedTextField(
                    value = newTime,
                    onValueChange = { newTime = it },
                    label = { Text("Time") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(timeSlot.copy(time = newTime, meal = viewModel.getMealByID(newMealId)))
                onDismiss()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ScheduleScreenPreview() {
    ScheduleScreen(navController = NavHostController(LocalContext.current))
}