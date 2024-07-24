package com.example.lemmecook_frontend.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemmecook_frontend.activities.ui.theme.LemmeCookFrontendTheme

class ExploreMain : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemmeCookFrontendTheme {
                ExploreMainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreMainScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "Welcome, Sam")
            Text(text = "What would you like to", style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            ),)
            Text(text = "cook today?", style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            ),)
        }

    }
}
