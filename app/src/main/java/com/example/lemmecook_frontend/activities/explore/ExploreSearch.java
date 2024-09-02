package com.example.lemmecook_frontend.activities.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.models.data.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExploreSearch extends AppCompatActivity {
    List<Recipe> recentSearchedRecipe = new ArrayList<>();
    private ImageButton ibBack;
    private ImageView ivSearch;
    private HashMap<String, String> filterOptionMap = new HashMap<>();
    private List<String> filterSearchByIngredient = new ArrayList<>();
    private ImageButton ibFilter;
    private ActivityResultLauncher<Intent> filterLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialData();

        ibBack = findViewById(R.id.imageButtonBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, recent_search.newInstance(recentSearchedRecipe));
        fragmentTransaction.commit();

        ivSearch = findViewById(R.id.imageViewSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, search_result.newInstance(filterOptionMap, filterSearchByIngredient));
                fragmentTransaction.commit();
            }
        });

        ibFilter = findViewById(R.id.imageButtonFilter);
        ibFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExploreSearch.this, ExploreFilter.class);
                filterLauncher.launch(intent);
            }
        });

        filterLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Retrieve the data returned from ExploreFilter
                        Intent data = result.getData();
                        if (data != null) {
                            filterOptionMap =
                                    (HashMap<String, String>) data.getSerializableExtra("filterOptionMap");
                            filterSearchByIngredient =
                                    data.getStringArrayListExtra("filterSearchByIngredient");
                        }
                    }
                }
        );
    }

    private void initialData() {
        recentSearchedRecipe.add(new Recipe(637942, "Chicken Arrozcaldo", "https://img.spoonacular.com/recipes/637942-312x231.jpg", "jpg"));
        recentSearchedRecipe.add(new Recipe(157375, "Steamy Creamy Mushroom Risotto", "https://img.spoonacular.com/recipes/157375-312x231.jpg", "jpg"));
        recentSearchedRecipe.add(new Recipe(649985, "Light and Chunky Chicken Soup", "https://img.spoonacular.com/recipes/649985-312x231.jpg", "jpg"));
        recentSearchedRecipe.add(new Recipe(660283, "SLOW COOKER CHICKEN GUMBO SOUP", "https://img.spoonacular.com/recipes/660283-312x231.jpg", "jpg"));

        filterOptionMap.put("Diet", "All");
        filterOptionMap.put("Category", "All");
        filterOptionMap.put("Preparation time", "All");
        filterOptionMap.put("Sort by", "All");
        filterOptionMap.put("Difficulty", "All");
    }

}