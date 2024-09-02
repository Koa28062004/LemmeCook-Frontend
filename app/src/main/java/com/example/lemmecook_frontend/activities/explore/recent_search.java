package com.example.lemmecook_frontend.activities.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.models.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public class recent_search extends Fragment {
    private ImageView img1, img2, img3, img4;
    private TextView tv1, tv2, tv3, tv4;
    List<Recipe> recentSearchedRecipe = new ArrayList<>();

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
        return view;
    }
}