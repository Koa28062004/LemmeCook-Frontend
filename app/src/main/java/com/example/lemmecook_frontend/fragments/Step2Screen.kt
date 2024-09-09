package com.example.lemmecook_frontend.fragments

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemmecook_frontend.api.MealApi
import com.example.lemmecook_frontend.models.health.DietDataModel
import com.example.lemmecook_frontend.models.request.DietsRequest
import com.example.lemmecook_frontend.models.response.StatusResponse
import com.example.lemmecook_frontend.singleton.UserSession
import com.example.lemmecook_frontend.utilities.ApiUtility
import com.google.accompanist.flowlayout.FlowRow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Step2Screen() {
    var selectedChips by remember { mutableStateOf(setOf<String>()) }
    val diets = remember { mutableStateOf<List<DietDataModel>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        getDietsData(context, diets)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
//        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Skip Button and Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("")
            Text(
                text = "Skip",
                color = Color.Blue,
                modifier = Modifier.clickable {  }
            )
        }

        // Question and Subtext
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Do you follow any of these diets?",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp,
                modifier = Modifier.padding(top = 100.dp)
            )
            Text(
                text = "To offer you the best tailored diet experience we need to know more information about you.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 30.dp)
            )
        }

        // FlowRow with Chips
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 60.dp)
        ) {
            diets.value.forEach { diet ->
                Chip(
                    text = diet.diet,
                    isSelected = selectedChips.contains(diet.diet),
                    onChipClick = { chipText ->
                        selectedChips = if (selectedChips.contains(chipText)) {
                            selectedChips - chipText
                        } else {
                            selectedChips + chipText
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }
        }

        // Row to align circles and button horizontally
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Slider with circles
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                SliderCircle(Color(0xFFDADADA))
                SliderCircle(Color(0xFFDADADA))
                SliderCircle(Color(0xFF55915E))
            }

            Spacer(modifier = Modifier.height(320.dp))
            Spacer(modifier = Modifier.width(16.dp))

            TextButton(
                onClick = {
                    val userId = UserSession.userId
                    Log.d("Step2Screen", "Selected Chips: $selectedChips")
                    addDietsToUser(context, userId, selectedChips)
                },
                modifier = Modifier
                    .weight(1f)
                    .background(Color(86, 146, 95)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }
    }
}

fun getDietsData(context: Context, diets: MutableState<List<DietDataModel>>) {
    val mealApi = ApiUtility.getApiClient().create(MealApi::class.java)

    mealApi.getDiets().enqueue(object : Callback<Map<String, List<DietDataModel>>> {
        override fun onResponse(
            call: Call<Map<String, List<DietDataModel>>>,
            response: Response<Map<String, List<DietDataModel>>>
        ) {
            Log.d("Step2Screen", "Response code: ${response.code()}")
            Log.d("Step2Screen", "Response body: ${response.body()}")

            if (response.isSuccessful) {
                response.body()?.get("diets")?.let { dietsList ->
                    diets.value = dietsList
                }
            } else {
                Log.e("Step2Screen", "Response error code: ${response.code()}")
                Toast.makeText(context, "Get Diets failed", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<Map<String, List<DietDataModel>>>, t: Throwable) {
            Log.e("Step2Screen", "Failure: ${t.message}")
            Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG).show()
        }
    })
}

fun addDietsToUser(context: Context, userId: String?, selectedDiets: Set<String>) {
    if (userId == null) {
        Toast.makeText(context, "User ID is missing", Toast.LENGTH_LONG).show()
        return
    }

    val mealApi = ApiUtility.getApiClient().create(MealApi::class.java)
    val dietsData = DietsRequest(userId, selectedDiets.toList())

    Log.d("Step2Screen", "Sending request body: $dietsData")

    mealApi.addUserDiets(dietsData).enqueue(object : Callback<StatusResponse> {
        override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
            if (response.isSuccessful) {
                val statusResponse = response.body()
                if (statusResponse?.status == "success") {
                    Toast.makeText(context, "Diets added successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to add diets: ${statusResponse?.status}", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
            Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG).show()
        }
    })
}


//@Preview(showBackground = true)
@Composable
fun Step2ScreenPreview() {
    Step2Screen()
}