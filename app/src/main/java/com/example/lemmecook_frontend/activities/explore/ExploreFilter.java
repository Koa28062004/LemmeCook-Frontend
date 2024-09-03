package com.example.lemmecook_frontend.activities.explore;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lemmecook_frontend.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExploreFilter extends AppCompatActivity {
    private HashMap<String, String> filterOptionMap = new HashMap<>();
    private List<String> filterSearchByIngredient = new ArrayList<>();
    private ImageButton ibBack;
    private Button btnReset, btnConfirm;
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

                handleEventDiet();
                handleEventCategory();
                handleEventPreparationTime();
                handleEventSortBy();
                handleEventDifficulty();
            }
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

}