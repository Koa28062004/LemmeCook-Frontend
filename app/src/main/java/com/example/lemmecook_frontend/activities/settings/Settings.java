package com.example.lemmecook_frontend.activities.settings;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.adapter.FavoriteRecipeAdapter;
import com.example.lemmecook_frontend.fragments.EditProfileComponentJava;
import com.example.lemmecook_frontend.fragments.ProgressFragment;
import com.example.lemmecook_frontend.models.recipe.Recipe;
import com.example.lemmecook_frontend.singleton.UserSession;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    private RecyclerView rvFavorite;
    private List<Recipe> favoriteRecipes;
    private FavoriteRecipeAdapter adapter;
    private ImageButton ibThreeDots;
    boolean isOpenEditProfileFragment = false;
    private NavHostController navController;
    private TextView tvName;
    private ImageView ivAvatar;

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view2, new ProgressFragment());
        fragmentTransaction.commit();

        SharedViewModelSettings sharedViewModel = new ViewModelProvider(this).get(SharedViewModelSettings.class);
        navController = sharedViewModel.getData();
        if (navController == null) {
            Log.e("Settings", "NavHostController is not available");
        }

        ibThreeDots = findViewById(R.id.imageButtonThreeDots);
        ibThreeDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpenEditProfileFragment) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayoutEditProfile, EditProfileComponentJava.newInstance());
                    fragmentTransaction.commit();
                }
                else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment editProfileFragment = fragmentManager.findFragmentById(R.id.framelayoutEditProfile);
                    if (editProfileFragment != null) {
                        fragmentTransaction.remove(editProfileFragment);
                        fragmentTransaction.commit();
                    }
                }

                isOpenEditProfileFragment = !isOpenEditProfileFragment;
            }
        });

        tvName = findViewById(R.id.textViewName);
        tvName.setText(UserSession.INSTANCE.getFullName());

        ivAvatar = findViewById(R.id.ImageViewAvatar);
        Uri avatarUri = UserSession.INSTANCE.getAvatar();
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.avatar)
                .circleCrop();
        if (avatarUri != null) {
            Glide.with(this)
                    .load(avatarUri.toString())
                    .apply(requestOptions)
                    .into(ivAvatar);
        } else {
            // If avatarUri is null, load the default avatar
            Glide.with(this)
                    .load(R.drawable.avatar)
                    .apply(requestOptions)
                    .into(ivAvatar);
        }
    }

    private List<Recipe> getFavoriteRecipes() {
        // Dummy data, replace with your data retrieval logic
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(637942, "Chicken Arrozcaldo", "https://img.spoonacular.com/recipes/637942-312x231.jpg", "jpg"));
        recipes.add(new Recipe(157375, "Steamy Creamy Mushroom Risotto", "https://img.spoonacular.com/recipes/157375-312x231.jpg", "jpg"));
        recipes.add(new Recipe(649985, "Light and Chunky Chicken Soup", "https://img.spoonacular.com/recipes/649985-312x231.jpg", "jpg"));
        recipes.add(new Recipe(660283, "Slow Cooker Chicken Gumbo Soup", "https://img.spoonacular.com/recipes/660283-312x231.jpg", "jpg"));
        // Add more recipes
        return recipes;
    }

    private void changeUsername(int userId, String newUsername) {
        // ...
    }

    private void changePassword(int userId, String oldPassword, String newPassword) {
        // ...
    }

    private void changeFullName(int userId, String newFullName) {
        // ...
    }

    private void changeAvatar(int userId, String newAvatar) {
        // ...
    }
}