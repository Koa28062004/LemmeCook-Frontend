package com.example.lemmecook_frontend.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemmecook_frontend.R

@Composable
fun OnboardScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboard),
            contentDescription = "Healthy Salad",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
                .clip(RoundedCornerShape(30.dp))
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Enjoy your lunch time!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        Text(
            text = "Just relax and not overthink what to eat. \nThis is in our side with our personalized meal plans \njust prepared and adapted to your needs.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 15.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

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
                SliderCircle(Color(0xFF55915E))
                SliderCircle(Color(0xFFDADADA))
                SliderCircle(Color(0xFFDADADA))
            }

            Spacer(modifier = Modifier.width(16.dp))

            TextButton(
                onClick = { /* Handle click */ },
                modifier = Modifier
                    .height(50.dp)
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

@Composable
fun SliderCircle(color: Color) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .background(color, CircleShape)
    )

    Spacer(modifier = Modifier.width(8.dp))
}

@Preview(showBackground = true)
@Composable
fun OnboardScreenPreview() {
    OnboardScreen()
}
