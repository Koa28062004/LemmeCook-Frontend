package com.example.lemmecook_frontend.fragments

import ProgressComponent
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.lemmecook_frontend.activities.MainActivity

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
        allowChange = false,
        difficult = 4,
        rate = 3
    )
}

@Composable
fun RecipeCongratsScreen(navHostController: NavHostController) {
    // fetch data from database
    RecipeCongrats(
        currentCalo = 1289,
        currentFat = 290,
        currentPro = 580,
        currentCarb = 850,
        goalFat = 1000,
        goalPro = 1000,
        goalCarb = 1000,
        allowChange = false,
        difficult = 5,
        rate = 5
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
    allowChange: Boolean,
    difficult: Int,
    rate: Int
) {
    var difficulty by remember { mutableIntStateOf(difficult) }
    var rating by remember { mutableIntStateOf(rate) }
    val context = LocalContext.current

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

        ThreeDotMenu(
            buttonItems = listOf(
                MenuItem("Add to Favorites") {/* TODO: Add to favorites backend */},
                MenuItem("Share") {/* TODO: Share this recipe */}
            ),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 12.dp)
        )

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

            DifficultyRating(
                rating = difficulty,
                onRatingChanged = {difficulty = it},
                criteria = "difficulty",
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp)
            )

            DifficultyRating(
                rating = rating,
                onRatingChanged = {rating = it},
                criteria = "food",
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            DoneButton(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 15.dp),
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun DifficultyRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    criteria: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Rate the $criteria",
            fontSize = 21.sp,
            fontFamily = sf_pro_display,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row (
            modifier = Modifier.padding(top = 5.dp)
        ) {
            for (i in 1..5) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_outline),
                    contentDescription = "Star $i",
                    tint = if (i <= rating) Color(255, 203, 69) else Color(242, 242, 242),
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { onRatingChanged(i) }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
fun DoneButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(Color(86, 146, 95)),
        shape = RoundedCornerShape(24.dp) // Rounded edges
    ) {
        Text(
            text = "Done",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}