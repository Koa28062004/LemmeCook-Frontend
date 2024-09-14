package com.example.lemmecook_frontend.fragments

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.lemmecook_frontend.activities.NavHost.ExploreScreen
import com.example.lemmecook_frontend.models.health.ProgressDataModel
import com.example.lemmecook_frontend.models.recipe.RecipeInformation
import com.example.lemmecook_frontend.models.viewmodels.GoalViewModel
import com.example.lemmecook_frontend.models.viewmodels.ProgressViewModel
import com.example.lemmecook_frontend.singleton.GoalSession
import com.example.lemmecook_frontend.singleton.ProgressSession
import com.example.lemmecook_frontend.singleton.UserSession
import com.example.lemmecook_frontend.utilities.DateTimeUtility
import com.example.lemmecook_frontend.utilities.FavoriteApiUtility
import com.example.lemmecook_frontend.utilities.GoalApiUtility
import com.example.lemmecook_frontend.utilities.ProgressApiUtility

@Preview(showBackground = true)
@Composable
fun StateTestScreenForRecipeCongrats() {
    RecipeCongrats(
        recipeId = -1,
        rate = 3
    )
}

@Composable
fun RecipeCongratsScreen(navHostController: NavHostController, recipe: RecipeInformation) {
    val context = LocalContext.current
    val userId = UserSession.userId?.toInt() ?: -1
    val currentProgress = ProgressSession.progress

    // Update current progress
    val mealCalo = recipe.nutrition.nutrients.find { it.name == "Calories" }
    val mealFat = recipe.nutrition.nutrients.find { it.name == "Fat" }
    val mealPro = recipe.nutrition.nutrients.find { it.name == "Protein" }
    val mealCarb = recipe.nutrition.nutrients.find { it.name == "Carbohydrates" }

    val updatedProgress = ProgressDataModel (
        user_id = userId,
        date = DateTimeUtility.getCurrentDateAsString(),
        calories = currentProgress.calories + mealCalo?.amount?.toFloat()!!,
        fat = currentProgress.fat + mealFat?.amount?.toFloat()!!,
        protein = currentProgress.protein + mealPro?.amount?.toFloat()!!,
        carb = currentProgress.carb + mealCarb?.amount?.toFloat()!!
    )

    ProgressApiUtility.setProgress(updatedProgress, context)
    ProgressSession.progress = updatedProgress

    // Screen display
    RecipeCongrats(
        recipeId = recipe.id,
        imgUrl = recipe.image,
        rate = 5
    )
}

@Composable
fun RecipeCongrats(
    recipeId: Int,
    imgUrl: String = "",
    rate: Int
) {
    var rating by remember { mutableIntStateOf(rate) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = imgUrl,
                placeholder = painterResource(id = R.drawable.recipe_demo_img1),
                error = painterResource(id = R.drawable.recipe_demo_img1)
            ),
            contentDescription = "Recipe overview image",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentScale = ContentScale.Crop
        )

        ThreeDotMenu(
            buttonItems = listOf(
                MenuItem("Add to Favorites", FavoriteApiUtility.addToFavorites(
                    userId = UserSession.userId?.toInt() ?: -1,
                    mealId = recipeId,
                    context = context
                )),
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

            LazyColumn {
                item {
                    ProgressComponent(
                        allowChange = false,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                    )
                }
                item {
                    DifficultyRating(
                        rating = rating,
                        onRatingChanged = {rating = it},
                        criteria = "food",
                        modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp)
                    )
                }
            }

            DoneButton(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 15.dp),
                onClick = {
                    val intent = Intent(context, ExploreScreen::class.java)
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