package com.example.lemmecook_frontend.activities.explore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemmecook_frontend.BuildConfig;
import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.api.ApiRecipeJava;
import com.example.lemmecook_frontend.models.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExploreMain extends AppCompatActivity {
    private final String apiKey = BuildConfig.SPOON_API_KEY;
    private int number = 5;
    private List<Recipe> popularRecipes, recommendedRecipes, veganRecipes;
    private RecyclerView rvPopularRecipes, rvRecommendedRecipes, rvVeganRecipes;
    private PopularRecipeAdapter adapterPopularRecipes, adapterRecommendedRecipes, adapterVeganRecipes;
    private List<Recipe> recentSearchedRecipe;
    private TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        popularRecipes = new ArrayList<>();
        recommendedRecipes = new ArrayList<>();
        veganRecipes = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRecipeJava api = retrofit.create(ApiRecipeJava.class);

        // fetch recipes api
        fetchPopularRecipes(api, "chicken,rice", number, apiKey);
        fetchRecommendedRecipes(api, "beef", number, apiKey);
        fetchVeganRecipes(api, "salad", number, apiKey);

        rvPopularRecipes = findViewById(R.id.rvPopularRecipe);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPopularRecipes.setLayoutManager(layoutManager1);
        adapterPopularRecipes = new PopularRecipeAdapter(this, popularRecipes);
        rvPopularRecipes.setAdapter(adapterPopularRecipes);

        rvRecommendedRecipes = findViewById(R.id.rvRecommendedRecipe);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRecommendedRecipes.setLayoutManager(layoutManager2);
        adapterRecommendedRecipes = new PopularRecipeAdapter(this, recommendedRecipes);
        rvRecommendedRecipes.setAdapter(adapterRecommendedRecipes);

        rvVeganRecipes = findViewById(R.id.rvVeganRecipe);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvVeganRecipes .setLayoutManager(layoutManager3);
        adapterVeganRecipes = new PopularRecipeAdapter(this, veganRecipes);
        rvVeganRecipes.setAdapter(adapterVeganRecipes);

        tvSearch = findViewById(R.id.tvSearch);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExploreMain.this, ExploreSearch.class);
                recentSearchedRecipe = popularRecipes;
                startActivity(intent);
            }
        });
    }

    private void fetchPopularRecipes(ApiRecipeJava api, String ingredients, int number, String apiKey) {
        Call<List<Recipe>> call = api.getRecipes(ingredients, number, apiKey, "");
        call.enqueue(new retrofit2.Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    popularRecipes.addAll(response.body());
                    for (Recipe r : popularRecipes) {
                        Log.d("Popular recipe title", r.getTitle());
                        Log.d("id", String.valueOf(r.getId()));
                        Log.d("image", r.getImage());
                        Log.d("image type", r.getImageType());
                    }
                    adapterPopularRecipes.notifyDataSetChanged();
                } else {
                    // Handle the error
                }
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Handle the failure
            }
        });
    }

    private void fetchRecommendedRecipes(ApiRecipeJava api, String ingredients, int number, String apiKey) {
        Call<List<Recipe>> call = api.getRecipes(ingredients, number, apiKey, "");
        call.enqueue(new retrofit2.Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    recommendedRecipes.addAll(response.body());
                    for (Recipe r : recommendedRecipes) {
                        Log.d("Popular recipe title", r.getTitle());
                    }
                    adapterRecommendedRecipes.notifyDataSetChanged();
                } else {
                    // Handle the error
                }
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Handle the failure
            }
        });
    }

    private void fetchVeganRecipes(ApiRecipeJava api, String ingredients, int number, String apiKey) {
        Call<List<Recipe>> call = api.getRecipes(ingredients, number, apiKey, "vegan");
        call.enqueue(new retrofit2.Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    veganRecipes.addAll(response.body());
                    for (Recipe r : veganRecipes) {
                        Log.d("Popular recipe title", r.getTitle());
                    }
                    adapterVeganRecipes.notifyDataSetChanged();
                } else {
                    // Handle the error
                }
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Handle the failure
            }
        });
    }
}