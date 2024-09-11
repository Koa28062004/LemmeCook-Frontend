package com.example.lemmecook_frontend.activities.blog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.ui.theme.sf_pro_display

@Composable
fun BlogScreen() {
    Surface(color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomFontText(text = "News Blog", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun CustomFontText(
    text: String,
    fontWeight: FontWeight = FontWeight.SemiBold,
    color: Color = colorResource(id = R.color.gr_text),
    fontSize: TextUnit = 16.sp
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        fontFamily = sf_pro_display,
        fontSize = fontSize,
        color = color
    )
}

@Preview
@Composable
private fun BlogScreenPreview() {
    BlogScreen()
}