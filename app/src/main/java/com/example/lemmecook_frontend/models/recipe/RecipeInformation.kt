package com.example.lemmecook_frontend.models.recipe

import kotlinx.serialization.Serializable

@Serializable
data class RecipeInformation(
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val veryHealthy: Boolean,
    val cheap: Boolean,
    val veryPopular: Boolean,
    val sustainable: Boolean,
    val lowFodmap: Boolean,
    val weightWatcherSmartPoints: Int,
    val gaps: String,
    val preparationMinutes: Int,
    val cookingMinutes: Int,
    val aggregateLikes: Int,
    val healthScore: Int,
    val creditsText: String,
    val license: String,
    val sourceName: String,
    val pricePerServing: Double,
    val extendedIngredients: List<ExtendedIngredient>,
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val image: String,
    val imageType: String,
    val nutrition: Nutrition,
    val summary: String,
    val cuisines: List<String>,
    val dishTypes: List<String>,
    val diets: List<String>,
    val occasions: List<String>,
    val instructions: String?,
    val analyzedInstructions: AnalyzedInstruction,
    val originalId: Int?,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String
)


object SampleData {
    val sampleRecipeInformation = RecipeInformation(
        vegetarian = false,
        vegan = false,
        glutenFree = false,
        dairyFree = false,
        veryHealthy = true,
        cheap = false,
        veryPopular = true,
        sustainable = false,
        lowFodmap = false,
        weightWatcherSmartPoints = 10,
        gaps = "None",
        preparationMinutes = 10,
        cookingMinutes = 20,
        aggregateLikes = 150,
        healthScore = 85,
        creditsText = "Chef John",
        license = "CC BY 4.0",
        sourceName = "Spoonacular",
        pricePerServing = 4.5,
        extendedIngredients = listOf(
            ExtendedIngredient(
                id = 1,
                aisle = "Produce",
                consistency = "Solid",
                name = "Tomatoes",
                nameClean = "Tomatoes",
                original = "6pcs Tomatoes",
                originalName = "Tomatoes",
                amount = 6.0,
                unit = "pcs",
                meta = listOf(),
                image = "https://example.com/tomatoes.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 6.0, unitShort = "pcs", unitLong = "pieces"),
                    metric = MeasureUnit(amount = 6.0, unitShort = "pcs", unitLong = "pieces")
                )
            ),
            ExtendedIngredient(
                id = 2,
                aisle = "Dairy",
                consistency = "Solid",
                name = "Cheddar Cheese",
                nameClean = "Cheddar Cheese",
                original = "200g Cheddar Cheese",
                originalName = "Cheddar Cheese",
                amount = 200.0,
                unit = "g",
                meta = listOf("shredded"),
                image = "https://example.com/cheddar_cheese.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 7.05, unitShort = "oz", unitLong = "ounces"),
                    metric = MeasureUnit(amount = 200.0, unitShort = "g", unitLong = "grams")
                )
            ),

            ExtendedIngredient(
                id = 3,
                aisle = "Meat",
                consistency = "Solid",
                name = "Chicken Breast",
                nameClean = "Chicken Breast",
                original = "500g Chicken Breast",
                originalName = "Chicken Breast",
                amount = 500.0,
                unit = "g",
                meta = listOf("boneless", "skinless"),
                image = "https://example.com/chicken_breast.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 17.64, unitShort = "oz", unitLong = "ounces"),
                    metric = MeasureUnit(amount = 500.0, unitShort = "g", unitLong = "grams")
                )
            ),

            ExtendedIngredient(
                id = 4,
                aisle = "Baking",
                consistency = "Solid",
                name = "All-Purpose Flour",
                nameClean = "All-Purpose Flour",
                original = "2 cups All-Purpose Flour",
                originalName = "All-Purpose Flour",
                amount = 2.0,
                unit = "cups",
                meta = listOf(),
                image = "https://example.com/flour.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 2.0, unitShort = "cups", unitLong = "cups"),
                    metric = MeasureUnit(amount = 240.0, unitShort = "g", unitLong = "grams")
                )
            ),

            ExtendedIngredient(
                id = 5,
                aisle = "Produce",
                consistency = "Solid",
                name = "Spinach",
                nameClean = "Spinach",
                original = "100g Fresh Spinach",
                originalName = "Fresh Spinach",
                amount = 100.0,
                unit = "g",
                meta = listOf("fresh"),
                image = "https://example.com/spinach.jpg",
                measures = Measures(
                us = MeasureUnit(amount = 3.53, unitShort = "oz", unitLong = "ounces"),
                metric = MeasureUnit(amount = 100.0, unitShort = "g", unitLong = "grams")
                )
            ),

            ExtendedIngredient(
                id = 2,
                aisle = "Dairy",
                consistency = "Solid",
                name = "Cheddar Cheese",
                nameClean = "Cheddar Cheese",
                original = "200g Cheddar Cheese",
                originalName = "Cheddar Cheese",
                amount = 200.0,
                unit = "g",
                meta = listOf("shredded"),
                image = "https://example.com/cheddar_cheese.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 7.05, unitShort = "oz", unitLong = "ounces"),
                    metric = MeasureUnit(amount = 200.0, unitShort = "g", unitLong = "grams")
                )
            ),

            ExtendedIngredient(
                id = 3,
                aisle = "Meat",
                consistency = "Solid",
                name = "Chicken Breast",
                nameClean = "Chicken Breast",
                original = "500g Chicken Breast",
                originalName = "Chicken Breast",
                amount = 500.0,
                unit = "g",
                meta = listOf("boneless", "skinless"),
                image = "https://example.com/chicken_breast.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 17.64, unitShort = "oz", unitLong = "ounces"),
                    metric = MeasureUnit(amount = 500.0, unitShort = "g", unitLong = "grams")
                )
            ),

            ExtendedIngredient(
                id = 4,
                aisle = "Bakery",
                consistency = "Solid",
                name = "Bread",
                nameClean = "Bread",
                original = "2 slices Whole Grain Bread",
                originalName = "Whole Grain Bread",
                amount = 2.0,
                unit = "slices",
                meta = listOf("whole grain"),
                image = "https://example.com/bread.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 2.0, unitShort = "slices", unitLong = "slices"),
                    metric = MeasureUnit(amount = 2.0, unitShort = "slices", unitLong = "slices")
                )
            ),

            ExtendedIngredient(
                id = 6,
                aisle = "Spices and Seasonings",
                consistency = "Solid",
                name = "Black Pepper",
                nameClean = "Black Pepper",
                original = "1 tsp Black Pepper",
                originalName = "Black Pepper",
                amount = 1.0,
                unit = "tsp",
                meta = listOf("ground"),
                image = "https://example.com/black_pepper.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 1.0, unitShort = "tsp", unitLong = "teaspoon"),
                    metric = MeasureUnit(amount = 1.0, unitShort = "tsp", unitLong = "teaspoon")
                )
            ),

            ExtendedIngredient(
                id = 7,
                aisle = "Produce",
                consistency = "Solid",
                name = "Cucumber",
                nameClean = "Cucumber",
                original = "1 Cucumber",
                originalName = "Cucumber",
                amount = 1.0,
                unit = "",
                meta = listOf("fresh"),
                image = "https://example.com/cucumber.jpg",
                measures = Measures(
                    us = MeasureUnit(amount = 1.0, unitShort = "", unitLong = ""),
                    metric = MeasureUnit(amount = 1.0, unitShort = "", unitLong = "")
                )
            )

        ),
        id = 12345,
        title = "Healthy Salad",
        readyInMinutes = 30,
        servings = 4,
        sourceUrl = "https://example.com/recipe",
        image = "https://example.com/image.jpg",
        imageType = "jpg",
        nutrition = Nutrition(
            nutrients = listOf(Nutrient(
                    name = "Calories",
                    amount = 250.0,
                    unit = "kcal",
                    percentOfDailyNeeds = 12.5
                ), Nutrient(
                    name = "Protein",
                    amount = 15.0,
                    unit = "g",
                    percentOfDailyNeeds = 30.0
                ), Nutrient(
                    name = "Carbohydrates",
                    amount = 45.0,
                    unit = "g",
                    percentOfDailyNeeds = 15.0
                ), Nutrient(
                    name = "Fat",
                    amount = 10.0,
                    unit = "g",
                    percentOfDailyNeeds = 15.0
                ))
        ),
        summary = "A healthy and delicious salad.",
        cuisines = listOf("American"),
        dishTypes = listOf("Salad", "Main course"),
        diets = listOf("Vegetarian"),
        occasions = listOf("Lunch"),
        instructions = "Mix all ingredients together and serve.",
        analyzedInstructions = AnalyzedInstruction(
            name = "",
            steps = listOf(
                InstructionStep(
                    number = 1,
                    step = "Heat 2 teaspoons of sesame oil in a frying pan over medium heat.",
                    ingredients = listOf(
                        Ingredient(
                            id = 10120420,
                            name = "Tomatoes",
                        ),
                        Ingredient(
                            id = 10010062,
                            name = "Spinach"
                        )
                    )
                ),
                InstructionStep(
                    number = 2,
                    step = "Add 250 grams of chopped chicken breasts to the pan and cook until golden brown, about 5-7 minutes.",
                    ingredients = listOf(
                        Ingredient(
                            id = 10010062,
                            name = "Chicken Breast"
                        )
                    )
                ),
                InstructionStep(
                    number = 3,
                    step = "In a separate pan, melt 1 tablespoon of unsalted butter over medium heat.",
                    ingredients = listOf(
                        Ingredient(
                            id = 10010062,
                            name = "Cheddar Cheese"
                        )
                    )
                ),
                InstructionStep(
                    number = 4,
                    step = "Add 100 grams of fresh spinach and cook until wilted.",
                    ingredients = listOf(
                        Ingredient(
                            id = 5,
                            name = "Spinach"
                        )
                    )
                )
            )
        ),
        originalId = null,
        spoonacularScore = 95.0,
        spoonacularSourceUrl = "https://example.com/spoonacular"
    )
}