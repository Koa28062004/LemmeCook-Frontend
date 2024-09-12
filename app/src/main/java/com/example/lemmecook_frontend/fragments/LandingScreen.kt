package com.example.lemmecook_frontend.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
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
import com.example.lemmecook_frontend.activities.NavHost.Blog
import com.example.lemmecook_frontend.activities.NavHost.SignInScreen
import com.example.lemmecook_frontend.activities.NavHost.SignUpScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import com.example.lemmecook_frontend.api.UsersApi
import com.example.lemmecook_frontend.models.auth.LoginDataModel
import com.example.lemmecook_frontend.models.request.EmailRequest
import com.example.lemmecook_frontend.models.response.AuthResponse
import com.example.lemmecook_frontend.singleton.UserSession
import com.example.lemmecook_frontend.utilities.ApiUtility
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LandingScreen(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as Activity

    // Set up Google SignIn options
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    // Build GoogleSignInClient with the options
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    // Launcher for Google Sign-In
    val googleSignInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        GoogleSignInResult(result.data, navController, context)
    }

    // Handle Google Sign-In button click
    fun handleGoogleSignIn() {
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    // Composable UI code remains the same
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
                .offset(y = (-5).dp),
            contentScale = ContentScale.Crop
        )

        // Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White),
        ) {
            Text(
                text = "Healthy Recipes \nin your Hand.\nEvery Day.",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                modifier = Modifier.padding(vertical = 15.dp)
            )

            TextButton(
                onClick = { navController.navigateTo(SignInScreen.route) },
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .padding(start = 10.dp)
                    .background(Color(86, 146, 95)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Sign in with email",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Text(
                text = "or use social sign up",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Google Sign In Button
            TextButton(
                onClick = { handleGoogleSignIn() },
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .padding(start = 8.dp)
                    .background(Color(0xFFD7D7D7)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continue with Google",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Facebook Sign In Button
            TextButton(
                onClick = { },
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .padding(start = 8.dp)
                    .background(Color(0xFFD7D7D7)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_facebook_logo),
                        contentDescription = "Facebook Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continue with Facebook",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Don’t have an account yet?",
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = "Sign up",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable { navController.navigateTo(SignUpScreen.route) }
            )
        }
    }
}

fun GoogleSignInResult(data: Intent?, navController: NavHostController, context: Context) {
    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
    try {
        val account = task.getResult(ApiException::class.java)
        // Google Sign-In was successful
        account?.let {
            // make email not null
            val email = it.email ?: "default@example.com"
            val textPassword = "123"

            val usersApi = ApiUtility.getApiClient().create(UsersApi::class.java)
            val emailRequest = EmailRequest(
                textEmail = email
            )

            usersApi.googleCheckUserExist(emailRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val statusResponse = response.body()
                        if (statusResponse?.status == "success") {
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                            UserSession.userId = statusResponse.userId
                            navController.navigateTo(Blog.route)
                        } else if (statusResponse?.status == "Sign up") {
                            Toast.makeText(context, "Sign Up!", Toast.LENGTH_SHORT).show()
                            navController.navigate("choose_name/$email/$textPassword")
                        }
                    } else {
                        Toast.makeText(context, "Login failed: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_LONG).show()
                }
            })
        }
    } catch (e: ApiException) {
        // Handle the error
        e.printStackTrace()
    }
}

@Composable
fun LandingScreenPreview(navController: NavHostController) {
    LandingScreen(navController)
}
