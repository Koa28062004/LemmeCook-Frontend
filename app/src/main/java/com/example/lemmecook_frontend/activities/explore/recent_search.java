package com.example.lemmecook_frontend.activities.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.models.data.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class recent_search extends Fragment {
    private ImageView img1, img2, img3, img4;
    private TextView tv1, tv2, tv3, tv4;
    List<Recipe> recentSearchedRecipe = new ArrayList<>();
    private LinearLayout ll1, ll2, ll3, ll4;
    private List<LinearLayout> llList = new ArrayList<>();

    public recent_search() {
        // Required empty public constructor
    }
    public static recent_search newInstance(List<Recipe> recipes) {
        recent_search fragment = new recent_search();
        Bundle args = new Bundle();
        args.putParcelableArrayList("RECENT_RECIPES", new ArrayList<>(recipes)); // Convert List to ArrayList
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
        View view = inflater.inflate(R.layout.fragment_recent_search, container, false);
        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);

        if (getArguments() != null) {
            recentSearchedRecipe = getArguments().getParcelableArrayList("RECENT_RECIPES");
        }

        updateRecentSearch();

        ll1 = view.findViewById(R.id.ll1);
        ll2 = view.findViewById(R.id.ll2);
        ll3 = view.findViewById(R.id.ll3);
        ll4 = view.findViewById(R.id.ll4);
        llList.add(ll1);
        llList.add(ll2);
        llList.add(ll3);
        llList.add(ll4);

        for (LinearLayout l : llList) {
            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = recentSearchedRecipe.size();
                    int index = llList.indexOf(l);
                    Recipe recipeChosenByUser = recentSearchedRecipe.get(n - index - 1);
                    recentSearchedRecipe.add(recipeChosenByUser);
                    removeDuplicates(recentSearchedRecipe);
                    updateRecentSearch();

                    // NGO THIEN BAO
                    // recipeChosenByUser is the recipe chosen by user
                    // ...
                }
            });
        }
        return view;
    }

    void updateRecentSearch() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
        int n = recentSearchedRecipe.size();
        Glide.with(this)
                .load(recentSearchedRecipe.get(n - 1).getImage())
                .apply(requestOptions)
                .into(img1);
        Glide.with(this)
                .load(recentSearchedRecipe.get(n - 2).getImage())
                .apply(requestOptions)
                .into(img2);
        Glide.with(this)
                .load(recentSearchedRecipe.get(n - 3).getImage())
                .apply(requestOptions)
                .into(img3);
        Glide.with(this)
                .load(recentSearchedRecipe.get(n - 4).getImage())
                .apply(requestOptions)
                .into(img4);

        tv1.setText(recentSearchedRecipe.get(n - 1).getTitle());
        tv2.setText(recentSearchedRecipe.get(n - 2).getTitle());
        tv3.setText(recentSearchedRecipe.get(n - 3).getTitle());
        tv4.setText(recentSearchedRecipe.get(n - 4).getTitle());
    }

    public static void removeDuplicates(List<Recipe> recentSearchedRecipe) {
        Map<Integer, Recipe> uniqueRecipesMap = new LinkedHashMap<>();

        // Iterate from the end to ensure the last occurrence is kept
        for (int i = recentSearchedRecipe.size() - 1; i >= 0; i--) {
            Recipe recipe = recentSearchedRecipe.get(i);
            uniqueRecipesMap.put(recipe.getId(), recipe);
        }

        // Clear the original list and add back the recipes in reverse order
        recentSearchedRecipe.clear();
        List<Recipe> resultList = new ArrayList<>(uniqueRecipesMap.values());
        Collections.reverse(resultList);
        recentSearchedRecipe.addAll(resultList);
    }
}