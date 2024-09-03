package com.example.lemmecook_frontend.activities.explore;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.adapter.FilterOptionAdapter;
import com.example.lemmecook_frontend.adapter.IngredientOptionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExploreFilter extends AppCompatActivity {
    private HashMap<String, String> filterOptionMap = new HashMap<>();
    private List<String> filterSearchByIngredient = new ArrayList<>();
    private ImageButton ibBack;
    private Button btnReset, btnConfirm;
    private AutoCompleteTextView actvSearchIngredient;
    private List<String> ingredients = new ArrayList<>();
    private RecyclerView rvIngredientOption;
    private IngredientOptionAdapter adapterIngredientOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore_filter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            filterOptionMap = (HashMap<String, String>) bundle.getSerializable("filterOptionMap");
            filterSearchByIngredient = bundle.getStringArrayList("filterSearchByIngredient");
        }

        ibBack = findViewById(R.id.imageButtonBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        handleEventDiet();
        handleEventCategory();
        handleEventPreparationTime();
        handleEventSortBy();
        handleEventDifficulty();

        btnReset = findViewById(R.id.btnReset);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnReset.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F2F2")));
        btnConfirm.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("filterOptionMap", filterOptionMap);
                resultIntent.putStringArrayListExtra("filterSearchByIngredient", new ArrayList<>(filterSearchByIngredient));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterOptionMap.put("Diet", "All");
                filterOptionMap.put("Category", "All");
                filterOptionMap.put("Preparation time", "All");
                filterOptionMap.put("Sort by", "All");
                filterOptionMap.put("Difficulty", "All");
                filterSearchByIngredient.clear();

                adapterIngredientOption.notifyDataSetChanged();

                handleEventDiet();
                handleEventCategory();
                handleEventPreparationTime();
                handleEventSortBy();
                handleEventDifficulty();
            }
        });

        rvIngredientOption = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvIngredientOption.setLayoutManager(layoutManager);
        adapterIngredientOption = new IngredientOptionAdapter(filterSearchByIngredient);
        rvIngredientOption.setAdapter(adapterIngredientOption);

        actvSearchIngredient = findViewById(R.id.actv);
        generateFakeIngredients();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ingredients);
        actvSearchIngredient.setAdapter(adapter);
        actvSearchIngredient.setThreshold(1);
        actvSearchIngredient.setOnItemClickListener((parent, view, position, id) -> {
            String selectedIngredient = (String) parent.getItemAtPosition(position);
            filterSearchByIngredient.add(selectedIngredient);
            adapterIngredientOption.notifyDataSetChanged();
            actvSearchIngredient.setText("");
        });

    }

    private void handleEventDiet() {
        Button btnDietAll = findViewById(R.id.button_diet_all);
        Button btnDietVegan = findViewById(R.id.button_diet_vegan);
        Button btnDietVegetarian = findViewById(R.id.button_diet_vegetarian);
        Button btnDietGlutenFree = findViewById(R.id.button_diet_gluten_free);
        Button btnDietNutFree = findViewById(R.id.button_nut_free);
        List<Button> dietOption = new ArrayList<>();
        dietOption.add(btnDietAll);
        dietOption.add(btnDietVegan);
        dietOption.add(btnDietVegetarian);
        dietOption.add(btnDietGlutenFree);
        dietOption.add(btnDietNutFree);

        for (Button b : dietOption) {
            if (b.getText().toString().equals(filterOptionMap.get("Diet"))) {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                b.setTextColor(Color.WHITE);
            }
            else {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                b.setTextColor(Color.parseColor("#55915E"));
            }
        }

        for (Button b : dietOption) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterOptionMap.put("Diet", b.getText().toString());

                    for (Button b : dietOption) {
                        if (b.getText().toString().equals(filterOptionMap.get("Diet"))) {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                            b.setTextColor(Color.WHITE);
                        }
                        else {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                            b.setTextColor(Color.parseColor("#55915E"));
                        }
                    }
                }
            });
        }
    }

    private void handleEventCategory() {
        Button btnCategoryAll = findViewById(R.id.button_category_all);
        Button btnCategoryBreakfast = findViewById(R.id.button_category_breakfast);
        Button btnCategoryLunch = findViewById(R.id.button_category_lunch);
        Button btnCategoryDinner = findViewById(R.id.button_category_dinner);
        Button btnCategorySoup = findViewById(R.id.button_category_soup);
        Button btnCategorySalad = findViewById(R.id.button_category_salad);
        Button btnCategorySnack = findViewById(R.id.button_category_snack);
        List<Button> categoryOption = new ArrayList<>();
        categoryOption.add(btnCategoryAll);
        categoryOption.add(btnCategoryBreakfast);
        categoryOption.add(btnCategoryLunch);
        categoryOption.add(btnCategoryDinner);
        categoryOption.add(btnCategorySoup);
        categoryOption.add(btnCategorySalad);
        categoryOption.add(btnCategorySnack);

        for (Button b : categoryOption) {
            if (b.getText().toString().equals(filterOptionMap.get("Category"))) {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                b.setTextColor(Color.WHITE);
            }
            else {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                b.setTextColor(Color.parseColor("#55915E"));
            }
        }

        for (Button b : categoryOption) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterOptionMap.put("Category", b.getText().toString());

                    for (Button b : categoryOption) {
                        if (b.getText().toString().equals(filterOptionMap.get("Category"))) {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                            b.setTextColor(Color.WHITE);
                        }
                        else {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                            b.setTextColor(Color.parseColor("#55915E"));
                        }
                    }
                }
            });
        }
    }

    private void handleEventPreparationTime() {
        Button btnPreparationTimeAll = findViewById(R.id.button_preparation_time_all);
        Button btnPreparationTime15 = findViewById(R.id.button_preparation_time_15);
        Button btnPreparationTime30 = findViewById(R.id.button_preparation_time_30);
        Button btnPreparationTime45 = findViewById(R.id.button_preparation_time_45);
        List<Button> preparationTimeOption = new ArrayList<>();
        preparationTimeOption.add(btnPreparationTimeAll);
        preparationTimeOption.add(btnPreparationTime15);
        preparationTimeOption.add(btnPreparationTime30);
        preparationTimeOption.add(btnPreparationTime45);

        for (Button b : preparationTimeOption) {
            if (b.getText().toString().equals(filterOptionMap.get("Preparation time"))) {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                b.setTextColor(Color.WHITE);
            }
            else {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                b.setTextColor(Color.parseColor("#55915E"));
            }
        }

        for (Button b : preparationTimeOption) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterOptionMap.put("Preparation time", b.getText().toString());

                    for (Button b : preparationTimeOption) {
                        if (b.getText().toString().equals(filterOptionMap.get("Preparation time"))) {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                            b.setTextColor(Color.WHITE);
                        }
                        else {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                            b.setTextColor(Color.parseColor("#55915E"));
                        }
                    }
                }
            });
        }
    }

    private void handleEventSortBy() {
        Button btnSortByAll = findViewById(R.id.button_sort_by_all);
        Button btnSortByNewest = findViewById(R.id.button_sort_by_newest);
        Button btnSortByMostPopular = findViewById(R.id.button_sort_most_popular);
        List<Button> sortByOption = new ArrayList<>();
        sortByOption.add(btnSortByAll);
        sortByOption.add(btnSortByNewest);
        sortByOption.add(btnSortByMostPopular);

        for (Button b : sortByOption) {
            if (b.getText().toString().equals(filterOptionMap.get("Sort by"))) {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                b.setTextColor(Color.WHITE);
            }
            else {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                b.setTextColor(Color.parseColor("#55915E"));
            }
        }

        for (Button b : sortByOption) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterOptionMap.put("Sort by", b.getText().toString());

                    for (Button b : sortByOption) {
                        if (b.getText().toString().equals(filterOptionMap.get("Sort by"))) {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                            b.setTextColor(Color.WHITE);
                        }
                        else {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                            b.setTextColor(Color.parseColor("#55915E"));
                        }
                    }
                }
            });
        }
    }

    private void handleEventDifficulty() {
        Button btnDifficultyAll = findViewById(R.id.button_difficulty_all);
        Button btnDifficultyEasy = findViewById(R.id.button_difficulty_easy);
        Button btnDifficultyHard = findViewById(R.id.button_difficulty_hard);
        Button btnDifficultyExpert = findViewById(R.id.button_difficulty_expert);
        List<Button> difficultyOption = new ArrayList<>();
        difficultyOption.add(btnDifficultyAll);
        difficultyOption.add(btnDifficultyEasy);
        difficultyOption.add(btnDifficultyHard);
        difficultyOption.add(btnDifficultyExpert);

        for (Button b : difficultyOption) {
            if (b.getText().toString().equals(filterOptionMap.get("Difficulty"))) {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                b.setTextColor(Color.WHITE);
            }
            else {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                b.setTextColor(Color.parseColor("#55915E"));
            }
        }

        for (Button b : difficultyOption) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterOptionMap.put("Difficulty", b.getText().toString());

                    for (Button b : difficultyOption) {
                        if (b.getText().toString().equals(filterOptionMap.get("Difficulty"))) {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55915E")));
                            b.setTextColor(Color.WHITE);
                        }
                        else {
                            b.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                            b.setTextColor(Color.parseColor("#55915E"));
                        }
                    }
                }
            });
        }
    }

    void generateFakeIngredients() {
        ingredients = Arrays.asList(
                "Almonds", "Anchovy", "Apple", "Apricots", "Artichoke", "Arugula", "Asparagus", "Avocado",
                "Bacon", "Baking Powder", "Baking Soda", "Balsamic Vinegar", "Banana", "Basil", "Bay Leaves",
                "Beef", "Beetroot", "Bell Pepper", "Black Beans", "Black Pepper", "Blackberries", "Blueberry",
                "Bok Choy", "Bran", "Brie Cheese", "Broccoli", "Brown Sugar", "Brussels Sprouts", "Butter",
                "Buttermilk", "Cabbage", "Cacao", "Cajun Spice", "Cajun Seasoning", "Canned Tomatoes",
                "Cantaloupe", "Capers", "Carrot", "Cashews", "Cauliflower", "Celery", "Cheddar Cheese",
                "Cherries", "Chicken", "Chicken Broth", "Chickpeas", "Chili Powder", "Chives", "Chocolate",
                "Cilantro", "Cinnamon", "Cloves", "Coconut", "Coconut Milk", "Coconut Oil", "Cod",
                "Coffee", "Corn", "Coriander", "Cottage Cheese", "Couscous", "Crab", "Cream Cheese",
                "Cream of Tartar", "Cucumber", "Cumin", "Curry Powder", "Dates", "Dijon Mustard",
                "Dill", "Duck", "Eggplant", "Eggs", "Fennel", "Feta Cheese", "Figs", "Fish Sauce",
                "Flour", "French Beans", "Garlic", "Ghee", "Ginger", "Goat Cheese", "Grapefruit",
                "Grapes", "Green Beans", "Green Onion", "Ground Beef", "Ground Turkey", "Halibut",
                "Hazelnuts", "Honey", "Hummus", "Iceberg Lettuce", "Jalapeño", "Jasmine Rice", "Kale",
                "Kefir", "Ketchup", "Kidney Beans", "Kimchi", "Kiwifruit", "Lamb", "Leeks", "Lemon",
                "Lentils", "Lettuce", "Lime", "Linguine", "Lobster", "Mackerel", "Mango", "Maple Syrup",
                "Marjoram", "Mayonnaise", "Melon", "Milk", "Mint", "Miso", "Mushrooms", "Mustard",
                "Nutmeg", "Nuts", "Oat Bran", "Oatmeal", "Oats", "Octopus", "Olive Oil", "Olives", "Onion",
                "Orange", "Oregano", "Oysters", "Papaya", "Paprika", "Parmesan Cheese", "Parsley",
                "Parsnip", "Pasta", "Peach", "Peanut Butter", "Peanuts", "Pear", "Peas", "Pecans",
                "Pepper", "Pesto", "Pickles", "Pine Nuts", "Pineapple", "Pinto Beans", "Pistachios",
                "Plum", "Pomegranate", "Poppy Seeds", "Pork", "Potato", "Pumpkin", "Quinoa", "Radish",
                "Raisins", "Raspberries", "Red Beans", "Red Onion", "Red Pepper", "Rice", "Rice Noodles",
                "Ricotta Cheese", "Romaine Lettuce", "Rosemary", "Rye Bread", "Saffron", "Salmon",
                "Sardines", "Sausage", "Scallions", "Seaweed", "Sesame Oil", "Sesame Seeds", "Shallots",
                "Shrimp", "Snap Peas", "Snow Peas", "Sour Cream", "Soy Sauce", "Spaghetti", "Spinach",
                "Squash", "Steak", "Strawberries", "Sugar", "Sunflower Seeds", "Sweet Potato", "Swiss Chard",
                "Tarragon", "Tarragon Vinegar", "Thyme", "Tilapia", "Tofu", "Tomato", "Tomato Paste",
                "Trout", "Tuna", "Turkey", "Turnip", "Vanilla Extract", "Vegetable Oil", "Vinegar",
                "Walnuts", "Watercress", "Watermelon", "Whipping Cream", "White Beans", "White Pepper",
                "Whole Grain Bread", "Worcestershire Sauce", "Yams", "Yeast", "Yellow Beans", "Yogurt",
                "Zucchini"
        );
    }
}