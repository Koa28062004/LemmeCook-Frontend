package com.example.lemmecook_frontend.fragments

import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.lemmecook_frontend.activities.NavHost.SignInScreen
import com.example.lemmecook_frontend.activities.NavHost.SignUpScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

@Composable
fun LandingScreen(navController: NavHostController) {
    val context = LocalContext.current

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
                onClick = {

                },
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
                        fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Facebook Sign In Button
            TextButton(
                onClick = { /*TODO: Handle sign in with Facebook*/ },
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
                        fontSize = 18.sp)
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

@Composable
fun LandingScreenPreview(navController: NavHostController) {
    LandingScreen(navController)
}