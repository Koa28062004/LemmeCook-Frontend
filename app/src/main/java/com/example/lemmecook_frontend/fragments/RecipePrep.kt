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
import androidx.compose.material3.IconButton
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Preview(showBackground = true)
@Composable
fun StateTestScreenForRecipePrep() {
    RecipePrep()
}


@Composable
fun RecipePrep() {
    // Manage the current step state
    var currentStep by remember { mutableIntStateOf(1) }

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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrepStep(stepNumber = 1)
                RecipeSteps(stepsCount = 4, currentStep = currentStep)
                StepContent(
                    ingredients = listOf(
                        "Bacon" to "50 gr",
                        "Soy Sauce" to "200 ml"
                    ),
                    summary = "We tie the bacon with twine so that the skin is on the outside and one end and the other practically meet. Heat a little oil in a pressure cooker and mark the bacon all over until golden brown. We remove and discard the oil."
                )

                Spacer(modifier = Modifier.weight(1f))

                BottomButtonsSteps(
                    onPreviousClick = { if (currentStep > 1) currentStep-- },
                    onNextClick = { if (currentStep < 4) currentStep++ } // Adjust for 4 steps (3 + flag)
                )
            }
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Draw circles for each step and highlight the active step
        for (i in 1 until stepsCount) { // Loop until stepsCount (excluding the flag step)
            StepCircle(stepNumber = i, isActive = i == currentStep)
        }

        // Draw the flag icon as the last step
        FlagCircle(isActive = currentStep == stepsCount) // Check if the flag step is active
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
        modifier = Modifier.fillMaxWidth()
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
fun StepContent(ingredients: List<Pair<String, String>>, summary: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IngredientList(ingredients = ingredients)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = summary,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp),
            fontFamily = sf_pro_display,
            fontSize = 18.sp
        )
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
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
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
