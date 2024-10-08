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
import com.example.lemmecook_frontend.activities.NavHost.SignInScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import com.example.lemmecook_frontend.api.UsersApi
import com.example.lemmecook_frontend.models.request.EmailRequest
import com.example.lemmecook_frontend.models.response.AuthResponse
import com.example.lemmecook_frontend.utilities.ApiUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
    var textEmail by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var textConfirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val customGreen = Color(0xFF55915E)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

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
                text = "Create Account",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = "One more step from discovering the best recipes",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "and joining a happy and healthy community.",
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

            Spacer(modifier = Modifier.height(8.dp))

            // Confrim Password TextField with visibility toggle
            TextField(
                value = textConfirmPassword,
                onValueChange = { textConfirmPassword = it },
                label = { Text("Confirm Password") },
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
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (confirmPasswordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    IconButton(onClick = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }) {
                        Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                    }
                },
                shape = RoundedCornerShape(0.dp) // No top or side borders
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    signUpAction(
                        context,
                        navController,
                        textEmail,
                        textPassword,
                        textConfirmPassword
                    )
                },
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .padding(start = 10.dp) // Spacing between the buttons
                    .background(Color(86, 146, 95)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Sign up",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Already have an account?",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Sign in",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable { navController.navigateTo(SignInScreen.route) }
            )
        }
    }
}

fun signUpAction(
    context: Context,
    navController: NavHostController,
    textEmail: String,
    textPassword: String,
    textConfirmPassword: String
) {
    if (validateInputs(context, textEmail, textPassword, textConfirmPassword)) {
        if (textPassword != textConfirmPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
        } else {
            val usersApi = ApiUtility.getApiClient().create(UsersApi::class.java)
            val emailRequest = EmailRequest(
                textEmail = textEmail
            )

            usersApi.userCheckEmail(emailRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.isSuccessful) {
                        val statusResponse = response.body()
                        if (statusResponse?.status == "success") {
                            Toast.makeText(context, "Sign Up successful!", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigate("choose_name/$textEmail/$textPassword")
                        } else {
                            Toast.makeText(
                                context,
                                "1 - Sign Up failed: ${statusResponse?.status}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "2 - Sign Up failed: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }
}

private fun validateInputs(
    context: Context,
    textEmail: String,
    textPassword: String,
    textConfirmPassword: String
): Boolean {
    var isValid = true

    if (textEmail.isBlank() || textPassword.isBlank() || textConfirmPassword.isBlank()) {
        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
        isValid = false
    }

    return isValid
}

//@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview(navController: NavHostController) {
    SignUpScreen(navController)
}
