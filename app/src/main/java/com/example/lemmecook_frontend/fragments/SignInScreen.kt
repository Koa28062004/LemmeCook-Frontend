package com.example.lemmecook_frontend.fragments

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import com.example.lemmecook_frontend.activities.NavHost.LandingScreen
import com.example.lemmecook_frontend.activities.NavHost.ForgetPasswordScreen
import com.example.lemmecook_frontend.api.UsersApi
import com.example.lemmecook_frontend.models.auth.LoginDataModel
import com.example.lemmecook_frontend.models.response.StatusResponse
import com.example.lemmecook_frontend.singleton.UserSession
import com.example.lemmecook_frontend.utilities.ApiUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavHostController) {
    var textEmail by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                text = "Welcome Back!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = "It is so nice to see you!",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "We have new recipes for you!",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Email TextField
            TextField(
                value = textEmail,
                onValueChange = { textEmail = it },
                label = { Text("Email") },
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
                value = textPassword,
                onValueChange = { textPassword = it },
                label = { Text("Password") },
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                    }
                },
                shape = RoundedCornerShape(0.dp) // No top or side borders
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { SignInAction(context, textEmail, textPassword) },
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .padding(start = 10.dp) // Spacing between the buttons
                    .background(Color(86, 146, 95)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Sign in",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Clickable Texts
            Text(
                text = "or back to landing screen",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable { navController.navigateTo(LandingScreen.route) }
            )

            Text(
                text = "Forgot your password?",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable { navController.navigateTo(ForgetPasswordScreen.route) }
            )
        }
    }
}

fun SignInAction(context: Context, textEmail: String, textPassword: String) {
    if (validateInputs(context, textEmail, textPassword)) {
        val usersApi = ApiUtility.getApiClient().create(UsersApi::class.java)
        val loginData = LoginDataModel(
            email = textEmail,
            password = textPassword
        )

        usersApi.userLogin(loginData).enqueue(object : Callback<StatusResponse> {
            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                if (response.isSuccessful) {
                    val statusResponse = response.body()
                    if (statusResponse?.status == "success") {
                        Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                        UserSession.userId = statusResponse.userId
                    } else {
                        Toast.makeText(context, "1 - Login failed: ${statusResponse?.status}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "2 - Login failed: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG).show()
            }
        })
    }

}

private fun validateInputs(context: Context, textEmail: String, textPassword: String): Boolean {
    var isValid = true

    if (textEmail.isBlank() || textPassword.isBlank()) {
        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
        isValid = false
    }

    return isValid
}

//@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(navController: NavHostController) {
    SignInScreen(navController)
}
