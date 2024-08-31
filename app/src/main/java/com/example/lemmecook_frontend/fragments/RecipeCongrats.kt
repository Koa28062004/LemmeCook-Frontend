package com.example.lemmecook_frontend.fragments

import ProgressComponent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.ui.theme.sf_pro_display
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Preview(showBackground = true)
@Composable
fun StateTestScreenForRecipeCongrats() {
    RecipeCongrats(
        currentCalo = 1289,
        currentFat = 290,
        currentPro = 580,
        currentCarb = 850,
        goalFat = 1000,
        goalPro = 1000,
        goalCarb = 1000,
        allowChange = false
    )
}


@Composable
fun RecipeCongrats(
    currentCalo: Int,
    currentFat: Int,
    currentPro: Int,
    currentCarb: Int,
    goalFat: Int,
    goalPro: Int,
    goalCarb: Int,
    allowChange: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.recipe_demo_img1),
            contentDescription = "Recipe overview image",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.utils_icon),
                contentDescription = "Back arrow icon",
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
                ),

            ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Congratulations!",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = sf_pro_display,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(0.dp, 14.dp, 0.dp, 4.dp)
                )

                Text(
                    text = "We wish you had a great time!",
                    fontWeight = FontWeight.Normal,
                    fontFamily = sf_pro_display,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
                )
            }

            ProgressComponent(
                currentCalo = currentCalo,
                currentFat = currentFat,
                currentPro = currentPro,
                currentCarb = currentCarb,
                goalFat = goalFat,
                goalPro = goalPro,
                goalCarb = goalCarb,
                allowChange = allowChange,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            )
        }
    }
}
