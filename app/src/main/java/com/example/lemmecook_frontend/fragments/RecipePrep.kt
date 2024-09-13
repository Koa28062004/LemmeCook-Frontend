package com.example.lemmecook_frontend.fragments

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.lemmecook_frontend.activities.NavHost.RecipeCongratsScreen
import com.example.lemmecook_frontend.activities.NavHost.RecipeOverviewScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateTo
import com.example.lemmecook_frontend.models.recipe.ExtendedIngredient
import com.example.lemmecook_frontend.models.recipe.InstructionStep
import com.example.lemmecook_frontend.models.recipe.RecipeInformation
import com.example.lemmecook_frontend.models.recipe.SampleData
import com.example.lemmecook_frontend.singleton.UserSession
import com.example.lemmecook_frontend.utilities.FavoriteApiUtility
import com.google.gson.Gson

@Preview(showBackground = true)
@Composable
fun StateTestScreenForRecipePrep() {
    val navHostController = rememberNavController()
    RecipePrep(
        navController = navHostController,
        recipeInfo = SampleData.sampleRecipeInformation
    )
}

@Composable
fun RecipePrepScreen(navController: NavHostController, recipeInfo: RecipeInformation?) {
    RecipePrep(navController, recipeInfo ?: SampleData.sampleRecipeInformation)
}

@Composable
fun RecipePrep(
    navController: NavHostController,
    recipeInfo: RecipeInformation
) {
    // Manage the current step state
    var currentStep by remember { mutableIntStateOf(1) }
    val totalStep = recipeInfo.analyzedInstructions[0].steps.size

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

        ThreeDotMenu(
            buttonItems = listOf(
                MenuItem("Add to Favorites", FavoriteApiUtility.addToFavorites(
                    userId = UserSession.userId?.toInt() ?: -1,
                    mealId = recipeInfo.id,
                    context = LocalContext.current
                )),
                MenuItem("Share") {/* TODO: Share this recipe */},
                MenuItem("Quit cooking") {
                    navController.navigateTo("${RecipeOverviewScreen.route}/${recipeInfo.id}")
                }
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
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrepStep(stepNumber = 1)
                RecipeSteps(stepsCount = totalStep, currentStep = currentStep)
                StepContent(
                    instructionStep = recipeInfo.analyzedInstructions[0].steps[currentStep - 1],
                    extendedIngredient = recipeInfo.extendedIngredients
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            BottomButtonsSteps(
                onPreviousClick = {
                    if (currentStep > 1)
                        currentStep--
                },
                onNextClick = {
                    if (currentStep < totalStep)
                        currentStep++
                    else {
                        val recipeInfoJson = Gson().toJson(recipeInfo)
                        navController.currentBackStackEntry?.savedStateHandle?.set("recipeInfo", recipeInfoJson)
                        navController.navigateTo(RecipeCongratsScreen.route)
                    }
                }
            )
        }
    }
}

@Composable
fun PrepStep(stepNumber: Int) {
    Text(
        text = "Step $stepNumber",
        fontWeight = FontWeight.SemiBold,
        fontFamily = sf_pro_display,
        fontSize = 26.sp,
        modifier = Modifier.padding(0.dp, 14.dp, 0.dp, 4.dp)
    )
}

@Composable
fun RecipeSteps(stepsCount: Int, currentStep: Int) {
    // Determine the number of steps for each row
    val circlesPerRow = if (stepsCount <= 5) stepsCount else (stepsCount + 1) / 2

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        // First row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (stepsCount <= 5) {
                for (i in 1..<circlesPerRow) {
                    StepCircle(stepNumber = i, isActive = i == currentStep)
                }
                FlagCircle(isActive = currentStep == stepsCount)
            } else {
                for (i in 1..circlesPerRow) {
                    StepCircle(stepNumber = i, isActive = i == currentStep)
                }
            }
        }

        // Second row (only if there are more than 5 steps)
        if (stepsCount > 5) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                for (i in (circlesPerRow + 1) until stepsCount - 1) {
                    StepCircle(stepNumber = i, isActive = i == currentStep)
                }

                // Draw the flag icon as the last step
                FlagCircle(isActive = currentStep == stepsCount) // Check if the flag step is active
            }
        }
    }
}


@Composable
fun StepCircle(stepNumber: Int, isActive: Boolean) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(25.dp)
            .background(
                color = if (isActive) Color(0xFF4CAF50) else Color.Transparent,
                shape = CircleShape
            )
            .border(1.dp, color = Color.Black, shape = CircleShape)
    ) {
        Text(
            text = stepNumber.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isActive) Color.White else Color.Black
        )
    }
}

@Composable
fun FlagCircle(isActive: Boolean) {
    Icon(
        painter = painterResource(id = R.drawable.ic_flag), // Replace with your flag icon resource
        contentDescription = "Flag",
        tint = if (isActive) Color.White else Color.Black, // Adjust tint based on active state
        modifier = Modifier
            .size(25.dp)
            .background(
                color = if (isActive) Color(0xFF4CAF50) else Color.Transparent,
                shape = CircleShape
            )
            .border(1.dp, color = Color.Black, shape = CircleShape)
            .padding(8.dp)
    )
}



@Composable
fun IngredientList(ingredients: List<Pair<String, String>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height((50 * ingredients.size).dp)
    ) {
        items(ingredients) { ingredient ->
            IngredientItemStep(ingredientName = ingredient.first, ingredientQuantity = ingredient.second)
        }
    }
}

@Composable
fun IngredientItemStep(ingredientName: String, ingredientQuantity: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ingredientName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = sf_pro_display
        )
        Text(
            text = ingredientQuantity,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 8.dp),
            fontFamily = sf_pro_display
        )
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = Color.LightGray,
        modifier = Modifier.padding(horizontal = 30.dp)
    )
}

@Composable
fun StepContent(instructionStep: InstructionStep, extendedIngredient: List<ExtendedIngredient>) {
    val processedIngredients = mutableListOf<Pair<String, String>>()

    for (ingredient in instructionStep.ingredients) {
        val extendedInfo = extendedIngredient.find { it.name == ingredient.name }
        if (extendedInfo != null) {
            val processedIngredient = Pair(ingredient.name, "${extendedInfo.amount.toInt()} ${extendedInfo.unit}")
            processedIngredients.add(processedIngredient)
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            IngredientList(ingredients = processedIngredients)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

        }
        item {
            Text(
                text = instructionStep.step,
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp),
                fontFamily = sf_pro_display,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun BottomButtonsSteps(
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    // Buttons at the bottom
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
    ) {
        TextButton(
            onClick = { onPreviousClick() },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(end = 8.dp) // Spacing between the buttons
                .background(Color(242, 242, 242)),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = "Previous",
                color = Color.DarkGray,
                fontSize = 17.sp
            )
        }

        TextButton(
            onClick = { onNextClick() },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(start = 8.dp) // Spacing between the buttons
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
