package com.example.lemmecook_frontend.activities.settings;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.adapter.FavoriteRecipeAdapter;
import com.example.lemmecook_frontend.models.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    private RecyclerView rvFavorite;
    private List<Recipe> favoriteRecipes;
    private FavoriteRecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvFavorite = findViewById(R.id.rvFavorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        favoriteRecipes = getFavoriteRecipes(); // Method to retrieve your list of recipes
        adapter = new FavoriteRecipeAdapter(this, favoriteRecipes);
        rvFavorite.setAdapter(adapter);
    }

    private List<Recipe> getFavoriteRecipes() {
        // Dummy data, replace with your data retrieval logic
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(637942, "Chicken Arrozcaldo", "https://img.spoonacular.com/recipes/637942-312x231.jpg", "jpg"));
        recipes.add(new Recipe(157375, "Steamy Creamy Mushroom Risotto", "https://img.spoonacular.com/recipes/157375-312x231.jpg", "jpg"));
        recipes.add(new Recipe(649985, "Light and Chunky Chicken Soup", "https://img.spoonacular.com/recipes/649985-312x231.jpg", "jpg"));
        recipes.add(new Recipe(660283, "SLOW COOKER CHICKEN GUMBO SOUP", "https://img.spoonacular.com/recipes/660283-312x231.jpg", "jpg"));
        // Add more recipes
        return recipes;
    }
}