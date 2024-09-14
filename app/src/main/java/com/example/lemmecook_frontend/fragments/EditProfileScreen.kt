package com.example.lemmecook_frontend.fragments

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.lemmecook_frontend.activities.NavHost.SignInScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import com.example.lemmecook_frontend.components.AvatarPicker
import com.example.lemmecook_frontend.singleton.UserSession
import com.example.lemmecook_frontend.singleton.UserSession.userId
import com.example.lemmecook_frontend.utilities.UserApiUtility

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen() {
    var username by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var textConfirmNewPassword by remember { mutableStateOf("") }
    var oldPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmNewPasswordVisible by remember { mutableStateOf(false) }
    var avatarUri by remember { mutableStateOf(UserSession.avatar) }

    val customGreen = Color(0xFF55915E)
    val context = LocalContext.current
    UserSession.userId = "1"
    val userId = UserSession.userId



    // Fetch user data when the composable is first composed
    LaunchedEffect(userId) {
        if (userId != null) {
            UserApiUtility.getUserInfo(userId, context) { userInfo ->
                username = userInfo.username
                fullName = userInfo.fullName
                password = userInfo.password
            }
        }
    }

    // Content
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color.White)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
            modifier = Modifier.padding(top = 10.dp)
        )

        Text(
            text = "Wanna make a new life?",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Just be yourself!",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Avatar Picker
        AvatarPicker(
            avatarUri = avatarUri,
            onImagePicked = { uri ->
                avatarUri = uri
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Username TextField
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
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
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Full Name TextField
        TextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
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
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Old Password TextField with visibility toggle
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Old Password") },
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
            visualTransformation = if (oldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (oldPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { oldPasswordVisible = !oldPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle Old Password Visibility")
                }
            },
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // New Password TextField with visibility toggle
        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
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
            visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (newPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle New Password Visibility")
                }
            },
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm New Password TextField with visibility toggle
        TextField(
            value = textConfirmNewPassword,
            onValueChange = { textConfirmNewPassword = it },
            label = { Text("Confirm New Password") },
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
            visualTransformation = if (confirmNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmNewPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { confirmNewPasswordVisible = !confirmNewPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle Confirm New Password Visibility")
                }
            },
            shape = RoundedCornerShape(0.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save and Not Save buttons in a row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Not Save Button
            Button(
                onClick = {
                    // Handle "Not Save" action
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Not Save",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Save Button
            Button(
                onClick = {
                    if (newPassword != textConfirmNewPassword) {
                        Toast.makeText(context, "New passwords do not match", Toast.LENGTH_SHORT).show()
                    } else if (password.isBlank()) {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                    } else {
                        if (userId != null) {
                            UserApiUtility.updateUserInfo(
                                userId = userId,
                                username = username,
                                fullName = fullName,
                                password = password,
                                newPassword = newPassword,
                                context = context
                            ) { status ->
                                if (status == "success") {
                                    UserSession.userId = userId
                                    UserSession.username = username
                                    UserSession.fullName = fullName
                                    Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = customGreen),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen()
}
