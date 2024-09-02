package com.example.lemmecook_frontend.fragments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun Step2Screen() {
    var selectedChips by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
//        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Skip Button and Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("")
            Text(
                text = "Skip",
                color = Color.Blue,
                modifier = Modifier.clickable {  }
            )
        }

        // Question and Subtext
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Do you follow any of these diets?",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp,
                modifier = Modifier.padding(top = 100.dp)
            )
            Text(
                text = "To offer you the best tailored diet experience we need to know more information about you.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 30.dp)
            )
        }

        // FlowRow with Chips
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 60.dp)
        ) {
            listOf("None", "Vegan", "Pale", "Dukan", "Vegetarian", "Atkins", "Intermittent Fasting").forEach { item ->
                Chip(
                    text = item,
                    isSelected = selectedChips.contains(item),
                    onChipClick = { chipText ->
                        selectedChips = if (selectedChips.contains(chipText)) {
                            selectedChips - chipText
                        } else {
                            selectedChips + chipText
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }
        }

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
                SliderCircle(Color(0xFFDADADA))
                SliderCircle(Color(0xFFDADADA))
                SliderCircle(Color(0xFF55915E))
            }

            Spacer(modifier = Modifier.height(320.dp))
            Spacer(modifier = Modifier.width(16.dp))

            TextButton(
                onClick = {  },
                modifier = Modifier
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

//@Preview(showBackground = true)
@Composable
fun Step2ScreenPreview() {
    Step2Screen()
}