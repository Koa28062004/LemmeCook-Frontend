package com.example.lemmecook_frontend.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.lemmecook_frontend.singleton.GoalSession
import com.example.lemmecook_frontend.ui.theme.sf_pro_display

@Composable
fun SetCaloriesGoal(onGoalSet: (Float) -> Unit) {
    var newCalories by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { /* Handle dialog dismissal */ }) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Set Calories Goal",
                    fontFamily = sf_pro_display,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newCalories,
                    onValueChange = { newCalories = it },
                    label = { Text("Calories") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val goal = newCalories.toFloatOrNull() ?: 0f
                        onGoalSet(goal)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Set Goal")
                }
            }
        }
    }
}

@Composable
fun SetFatGoal(onGoalSet: (Float) -> Unit) {
    var newFat by remember { mutableStateOf("") }
    val context = LocalContext.current

    Dialog(onDismissRequest = { /* Handle dialog dismissal */ }) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Set Fat Goal",
                    fontFamily = sf_pro_display,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newFat,
                    onValueChange = { newFat = it },
                    label = { Text("Fat") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val goal = newFat.toFloatOrNull() ?: 0f
                        onGoalSet(goal)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Set Goal")
                }
            }
        }
    }
}

@Composable
fun SetProteinGoal(onGoalSet: (Float) -> Unit) {
    var newProtein by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { /* Handle dialog dismissal */ }) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Set Protein Goal",
                    fontFamily = sf_pro_display,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newProtein,
                    onValueChange = { newProtein = it },
                    label = { Text("Protein") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val goal = newProtein.toFloatOrNull() ?: 0f
                        onGoalSet(goal)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Set Goal")
                }
            }
        }
    }
}

@Composable
fun SetCarbGoal(onGoalSet: (Float) -> Unit) {
    var newCarb by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { /* Handle dialog dismissal */ }) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Set Carb Goal",
                    fontFamily = sf_pro_display,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newCarb,
                    onValueChange = { newCarb = it },
                    label = { Text("Carbs") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val goal = newCarb.toFloatOrNull() ?: 0f
                        onGoalSet(goal)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Set Goal")
                }
            }
        }
    }
}
