package com.example.lemmecook_frontend.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.lemmecook_frontend.R

@Composable
fun SignInScreen() {
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
//                .padding(top = 0.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
//                .offset(y = (-20).dp),
        ) {
            Text(
                text = "Welcome Back!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            TextButton(
                onClick = { },
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .padding(start = 10.dp) // Spacing between the buttons
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
                onClick = { /*TODO: Handle sign in with Google*/ },
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .padding(start = 8.dp) // Spacing between the buttons
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
                    .padding(start = 8.dp) // Spacing between the buttons
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
                text = "Don’t have an account yet? Sign up",
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}
