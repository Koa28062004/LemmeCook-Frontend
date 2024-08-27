package com.example.lemmecook_frontend.fragments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.ui.theme.sf_pro_display

@Preview(showBackground = true)
@Composable
fun StateTestScreen() {
    val title: String = "Healthy salad with salsa"
    val subtitles: List<String> = listOf("Lunch", "Easy", "15 minutes prep")
    val nutrients: Map<String, String> = mapOf(
            "Energy" to "100k",
            "Protein" to "8g",
            "Carbs" to "30g",
            "Fat" to "5g"
    )
    val ingredients: Map<String, String> = mapOf(
            "Chicken breasts" to "250g",
            "Unsalted butter" to "1tbsp",
            "Sesame oil and vegetable oil" to "2tsp",
            "Fresh ginger" to "2tsp",
            "Salad" to "400g",
            "Tomatoes" to "6pcs",
            "Cucumbers" to "10pcs",
            "Large eggs" to "100g"
    )
    RecipeOverview(
            title = title,
            subtitles = subtitles,
            nutrients = nutrients,
            ingredients = ingredients
    )
}
@Composable
fun RecipeOverview(
        title: String,
        subtitles: List<String>,
        nutrients: Map<String, String>,
        ingredients: Map<String, String>
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
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow_icon),
                contentDescription = "Back arrow icon",
                modifier = Modifier.size(70.dp)
            )
        }

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
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = sf_pro_display,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(0.dp, 14.dp, 0.dp, 4.dp)
                )

                Text(
                    text = "Thursday 10 AM",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = sf_pro_display,
                    fontSize = 17.sp,
                    color = Color(67, 67, 67)
                )

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
                            NutrientItem(value = nutrients.getValue("Energy"), label = "Energy")
                            NutrientItem(value = nutrients.getValue("Protein"), label = "Protein")
                            NutrientItem(value = nutrients.getValue("Carbs"), label = "Carbs")
                            NutrientItem(value = nutrients.getValue("Fat"), label = "Fat")
                        }
                    }
                }

                IngredientsSection(1)
                IngredientsList(ingredients = ingredients)
                BottomButtonsSection()
            }
        }
    }
}

@Composable
fun NutrientItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.Normal,
            fontFamily = sf_pro_display,
            fontSize = 16.sp
        )
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontFamily = sf_pro_display,
            fontSize = 19.sp,
            color = Color(67, 67, 67)
        )
    }
}

@Composable
fun IngredientsSection(
    initialServes: Int = 1,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {}
) {
    // Initialize and remember the serves state
    val serves = remember { mutableIntStateOf(initialServes) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, top = 4.dp, end = 24.dp, bottom = 10.dp),
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

        // Increment and Decrement Buttons as Text
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    if (serves.intValue > 1) {
                        serves.intValue -= 1
                        onDecrement() // Call the onDecrement function
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
                    onIncrement() // Call the onIncrement function
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
fun IngredientsList(ingredients: Map<String, String>) {
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
            items(ingredients.entries.toList()) { entry ->
                IngredientItem(name = entry.key, quantity = entry.value)
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
