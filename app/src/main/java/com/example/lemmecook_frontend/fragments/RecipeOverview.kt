package com.example.lemmecook_frontend.fragments

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.activities.NavHost.RecipePrepScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import com.example.lemmecook_frontend.activities.explore.ExploreMain
import com.example.lemmecook_frontend.models.recipe.ExtendedIngredient
import com.example.lemmecook_frontend.models.recipe.Nutrient
import com.example.lemmecook_frontend.models.recipe.RecipeInformation
import com.example.lemmecook_frontend.models.recipe.RecipeViewModel
import com.example.lemmecook_frontend.models.recipe.SampleData
import com.example.lemmecook_frontend.ui.theme.sf_pro_display

@Preview(showBackground = true)
@Composable
fun StateTestScreenForRecipeOverview() {
    val recipe = SampleData.sampleRecipeInformation
    val navHostController = rememberNavController()
    RecipeOverview(navHostController, recipe)
}

@Composable
fun RecipeOverviewScreen(navHostController: NavHostController) {
    val recipeViewModel: RecipeViewModel = viewModel()
    val recipe = recipeViewModel.recipeInformation.value
    if (recipe != null) {
        RecipeOverview(navController = navHostController, recipeInfo = recipe)
    }
}

@Composable
fun RecipeOverview(
        navController: NavHostController,
        recipeInfo: RecipeInformation,
) {
    val context = LocalContext.current
    val ingredientsFor1Portion = getIngredientFor1Portion(
        recipeInfo.extendedIngredients,
        recipeInfo.servings
    )
    val ingredientsOnDisplay = recipeInfo.extendedIngredients
    val serves = remember { mutableIntStateOf(recipeInfo.servings) }

    val recipeViewModel: RecipeViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = recipeInfo.image,
                placeholder = painterResource(id = R.drawable.recipe_demo_img1),
                error = painterResource(id = R.drawable.recipe_demo_img1)
            ),
            contentDescription = "Recipe overview image",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = {
                val intent = Intent(context, ExploreMain::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow_icon),
                contentDescription = "Back arrow icon",
                modifier = Modifier.size(60.dp)
            )
        }

        ThreeDotMenu(
            buttonItems = listOf(
                MenuItem("Add to Favorites") {/* TODO: Add to favorites backend */},
                MenuItem("Share") {/* TODO: Share this recipe */}
            ),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 300.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = recipeInfo.title,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = sf_pro_display,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(0.dp, 14.dp, 0.dp, 4.dp)
                )

                AnimatedTextLoop(texts = recipeInfo.dishTypes)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 15.dp)
                        .background(color = Color(244, 244, 244))
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Displaying nutritional values in a row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            NutrientItem("Calories", recipeInfo.nutrition.nutrients)
                            NutrientItem("Protein", recipeInfo.nutrition.nutrients)
                            NutrientItem("Carbohydrates", recipeInfo.nutrition.nutrients)
                            NutrientItem("Fat", recipeInfo.nutrition.nutrients)
                        }
                    }
                }

                IngredientsSection(
                    serves = serves,
                    ingredientsOnDisplay = ingredientsOnDisplay,
                    ingredientsPer1Serving = ingredientsFor1Portion
                )
                IngredientsList(ingredients = ingredientsOnDisplay)
                BottomButtonsSection(
                    onStartCookingClick = {
                        // update servings and ingredient amounts
                        recipeInfo.servings = serves.intValue
                        recipeInfo.extendedIngredients = ingredientsOnDisplay
                        recipeViewModel.setRecipeInformation(recipeInfo)

                        // navigate to RecipePrepScreen
                        navController.navigateTo(RecipePrepScreen.route)
                    },
                    onScheduleClick = {
                        // update servings and ingredient amounts
                        recipeInfo.servings = serves.intValue
                        recipeInfo.extendedIngredients = ingredientsOnDisplay
                        recipeViewModel.setRecipeInformation(recipeInfo)

                        // TODO: navigate to Schedule
                    }
                )
            }
        }
    }
}

@Composable
fun AnimatedTextLoop(texts: List<String>) {
    // Remember the current index and the text state
    var currentIndex by remember { mutableIntStateOf(0) }

    // LaunchedEffect to loop through the texts
    LaunchedEffect(Unit) {
        while (true) {
            // Delay for a certain time (e.g., 2 seconds)
            kotlinx.coroutines.delay(2000)

            // Update the index to the next item in the list
            currentIndex = (currentIndex + 1) % texts.size
        }
    }

    // Display the current text from the list
    Text(
        text = texts[currentIndex],
        fontWeight = FontWeight.SemiBold,
        fontFamily = sf_pro_display,
        fontSize = 17.sp,
        color = Color(67, 67, 67)
    )
}


@Composable
fun NutrientItem(label: String, nutrients: List<Nutrient>) {
    val nutrient = nutrients.find { it.name == label }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (nutrient != null) {
            Text(
                text = nutrient.amount.toInt().toString() + nutrient.unit,
                fontWeight = FontWeight.Normal,
                fontFamily = sf_pro_display,
                fontSize = 16.sp
            )

            Text(
                text = if (label == "Carbohydrates") "Carbs" else label,
                fontWeight = FontWeight.SemiBold,
                fontFamily = sf_pro_display,
                fontSize = 19.sp,
                color = Color(67, 67, 67)
            )
        }
    }
}

@Composable
fun IngredientsSection(
    serves: MutableIntState,
    ingredientsOnDisplay: List<ExtendedIngredient>,
    ingredientsPer1Serving: List<ExtendedIngredient>
) {
    // Initialize and remember the serves state
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, top = 4.dp, end = 26.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ingredients and serves text
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ingredients",
                fontWeight = FontWeight.SemiBold,
                fontFamily = sf_pro_display,
                fontSize = 19.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(18.dp))

            Text(
                text = "${serves.intValue} serves",
                fontWeight = FontWeight.Normal,
                fontFamily = sf_pro_display,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }

        // Increment and Decrement Buttons
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    if (serves.intValue > 1) {
                        serves.intValue
                        for (i in ingredientsPer1Serving.indices) {
                            ingredientsOnDisplay[i].amount -= ingredientsPer1Serving[i].amount
                        }
                    }
                },
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Transparent),
                border = BorderStroke(0.5.dp, Color.Black),
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                contentPadding = PaddingValues()
            ) {
                Text("-")
            }

            TextButton(
                onClick = {
                    serves.intValue += 1
                    for (i in ingredientsPer1Serving.indices) {
                        ingredientsOnDisplay[i].amount += ingredientsPer1Serving[i].amount
                    }
                },
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Transparent),
                border = BorderStroke(0.5.dp, Color.Black),
                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                contentPadding = PaddingValues()
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun IngredientsList(ingredients: List<ExtendedIngredient>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Set a fixed height for the ingredients list
            .padding(start = 40.dp, end = 15.dp)
    ) {
        // The scrollable list of ingredients
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, bottom = 10.dp) // Adjust for padding or spacing if needed
        ) {
            items(ingredients) { ingredient ->
                // Assuming IngredientItem has name and quantity parameters
                IngredientItem(
                    name = ingredient.name,
                    quantity = "${if (ingredient.amount % 1.0 == 0.0) ingredient.amount.toInt() else String.format("%.1f", ingredient.amount)} ${ingredient.unit}" // Display quantity and unit
                )
            }
        }

        // Gradient overlay at the bottom of the list
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp) // Height of the gradient effect
                .align(Alignment.BottomCenter) // Align the gradient to the bottom
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White)
                    )
                )
        )
    }
}

@Composable
fun IngredientItem(name: String, quantity: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = name,
            fontSize = 16.sp,
            color = Color.Black, // Normal text color
            modifier = Modifier.weight(1f)
        )
        Text(
            text = quantity,
            fontSize = 16.sp,
            color = Color.Gray, // Quantity is in gray
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun BottomButtonsSection(
    onScheduleClick: () -> Unit = {},
    onStartCookingClick: () -> Unit = {}
) {
    // Buttons at the bottom
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = { onScheduleClick() },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(end = 8.dp) // Spacing between the buttons
                .background(Color(242, 242, 242)),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = "Schedule for later",
                color = Color.DarkGray,
                fontSize = 17.sp
            )
        }

        TextButton(
            onClick = { onStartCookingClick() },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(start = 8.dp) // Spacing between the buttons
                .background(Color(86, 146, 95)),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = "Start cooking",
                color = Color.White,
                fontSize = 17.sp
            )
        }
    }
}

fun getIngredientFor1Portion(
    ingredients: List<ExtendedIngredient>,
    initialServes: Int
): List<ExtendedIngredient> {
    for (ingredient in ingredients) {
        ingredient.amount /= initialServes
    }
    return ingredients
}
