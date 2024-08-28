package com.example.lemmecook_frontend.activities.explore;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.api.ApiRecipeJava;
import com.example.lemmecook_frontend.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExploreMain extends AppCompatActivity {
    String apiKey = "YOUR_AKI_KEY";
    String ingredients = "apples,flour,sugar";
    int number = 5;
    private List<Recipe> recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    });

    recipes = new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiRecipeJava api = retrofit.create(ApiRecipeJava.class);

    // Call the API
    fetchRecipes(api);
    }

    private void fetchRecipes(ApiRecipeJava api) {
        Call<List<Recipe>> call = api.getRecipes(ingredients, number, apiKey);

        call.enqueue(new retrofit2.Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipes = response.body();
                    // Now you can use the recipes list as needed
                    for (Recipe r : recipes) {
                        Log.d("Recipes Title", r.getTitle());
                    }
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