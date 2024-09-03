package com.example.lemmecook_frontend.fragments

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import com.example.lemmecook_frontend.activities.NavHost.LandingScreen
import com.example.lemmecook_frontend.activities.NavHost.OnboardScreen
import com.example.lemmecook_frontend.activities.NavHost.ForgetPasswordScreen
import com.example.lemmecook_frontend.api.UsersApi
import com.example.lemmecook_frontend.models.data.RegisterDataModel
import com.example.lemmecook_frontend.models.response.StatusResponse
import com.example.lemmecook_frontend.utilities.ApiUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseNameScreen(navController: NavHostController, email: String?, password: String?) {
    var username by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }

    val context = LocalContext.current

    val customGreen = Color(0xFF55915E)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {

        // Top Image
        Image(
            painter = painterResource(id = R.drawable.landing_image),
            contentDescription = "Healthy Salad",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .align(Alignment.TopCenter)
                .offset(y = (-10).dp),
            contentScale = ContentScale.Crop
        )

        // Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
        ) {
            Text(
                text = "Choose Your Name!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = "What a lovely name!",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            // username TextField
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = customGreen,
                    unfocusedIndicatorColor = customGreen,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Red
                ),
                singleLine = true,
                shape = RoundedCornerShape(0.dp) // No top or side borders
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password TextField with visibility toggle
            TextField(
                value = fullName,
                onValueChange = { fullName= it },
                label = { Text("Fullname") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = customGreen,
                    unfocusedIndicatorColor = customGreen,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Red
                ),
                singleLine = true,
                shape = RoundedCornerShape(0.dp) // No top or side borders
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { ChooseNameAction(navController, context, username, fullName, email, password) },
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .padding(start = 10.dp) // Spacing between the buttons
                    .background(Color(86, 146, 95)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Confirm",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

fun ChooseNameAction(navController: NavHostController, context: Context, username: String, fullName: String, email: String?, password: String?) {
//    if (validateInputs(context, username, fullName)) {
//        val usersApi = ApiUtility.getApiClient().create(UsersApi::class.java)
//
//        val nonNullEmail: String = email!!
//        val nonNullPassword: String = password!!
//
//        val registerData = RegisterDataModel(
//            username = username,
//            fullName = fullName,
//            email = email,
//            password = password
//        )
//
//        usersApi.userRegister(registerData).enqueue(object : Callback<StatusResponse> {
//            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
//                if (response.isSuccessful) {
//                    val statusResponse = response.body()
//                    if (statusResponse?.status == "success") {
//                        Toast.makeText(context, "Register successful!", Toast.LENGTH_SHORT).show()
//                        navController.navigateTo(OnboardScreen.route)
//                    } else {
//                        Toast.makeText(context, "1 - Register failed: ${statusResponse?.status}", Toast.LENGTH_LONG).show()
//                    }
//                } else {
//                    Toast.makeText(context, "2 - Register failed: ${response.message()}", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
//                Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG).show()
//            }
//        })
//    }

    navController.navigateTo(OnboardScreen.route)
}

private fun validateInputs(context: Context, username: String, fullName: String): Boolean {
    var isValid = true

    if (username.isBlank() || fullName.isBlank()) {
        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
        isValid = false
    }

    return isValid
}

//@Preview(showBackground = true)
@Composable
fun ChooseNameScreenPreview(navController: NavHostController, email: String?, password: String?) {
    ChooseNameScreen(navController, email, password)
}
