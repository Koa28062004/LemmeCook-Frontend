package com.example.lemmecook_frontend.activities.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.adapter.FilterOptionAdapter;
import com.example.lemmecook_frontend.adapter.ViewPagerFoodAdapter;
import com.example.lemmecook_frontend.api.ApiRecipeJava;
import com.example.lemmecook_frontend.models.data.Recipe;
import com.example.lemmecook_frontend.models.data.RecipeResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class search_result extends Fragment {
    private RecyclerView rvOptionSearch;
    private HashMap<String, String> filterOptionMap = new HashMap<>();
    private List<String> filterSearchByIngredient = new ArrayList<>();
    private ViewPager2 viewPagerFood;
    private List<Recipe> recipes = new ArrayList<>();
    private TextView tvAnnounce;
    private String apiKey = "72c52e0281ea48a1bb1c9ce506e067a4";
    private int number = 5;
    private ViewPagerFoodAdapter adapterViewPagerFood;
    private String query;
    public search_result() {
        // Required empty public constructor
    }

    public static search_result newInstance(HashMap<String, String> filterOptionMap, List<String> filterSearchByIngredient, String query) {
        search_result fragment = new search_result();
        Bundle args = new Bundle();
        args.putSerializable("filterOptionMap", filterOptionMap);
        args.putSerializable("filterSearchByIngredient", (Serializable) filterSearchByIngredient);
        args.putSerializable("querySearch", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        if (getArguments() != null) {
            filterOptionMap = (HashMap<String, String>) getArguments().getSerializable("filterOptionMap");
            filterSearchByIngredient = (List<String>) getArguments().getSerializable("filterSearchByIngredient");
            query = getArguments().getString("querySearch");
        }

        rvOptionSearch = view.findViewById(R.id.recyclerViewOptionSearch);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rvOptionSearch.setLayoutManager(layoutManager);
        FilterOptionAdapter adapter = new FilterOptionAdapter(requireContext(), filterOptionMap, filterSearchByIngredient);
        rvOptionSearch.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRecipeJava api = retrofit.create(ApiRecipeJava.class);

        fetchSearchRecipes(api, number, apiKey, query);

        viewPagerFood = view.findViewById(R.id.viewPager);

        tvAnnounce = view.findViewById(R.id.textViewAnnounce);
        return view;
    }

    private void fetchSearchRecipes(ApiRecipeJava api, int number, String apiKey, String query) {
        Call<RecipeResponse> call = api.getComplexSearchRecipes(number, apiKey, query);
        call.enqueue(new retrofit2.Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, retrofit2.Response<RecipeResponse> response) {
                if (response.isSuccessful()) {
                    recipes = response.body().getResults();
                    for (Recipe r : recipes) {
                        Log.d("Search recipe", r.getTitle());
                    }
                    adapterViewPagerFood = new ViewPagerFoodAdapter(recipes);
                    viewPagerFood.setAdapter(adapterViewPagerFood);
                    tvAnnounce.setText(recipes.size() + " recipes based on your preferences");
                } else {
                    // Handle the error
                }
            }
            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                // Handle the failure
            }
        });
    }
}