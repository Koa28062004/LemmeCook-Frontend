package com.example.lemmecook_frontend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.models.recipe.Recipe;

import java.util.List;

public class FavoriteRecipeAdapter extends RecyclerView.Adapter<FavoriteRecipeAdapter.RecipeViewHolder> {
    private List<Recipe> favoriteRecipes;
    private Context context;

    public FavoriteRecipeAdapter(Context context, List<Recipe> favoriteRecipes) {
        this.context = context;
        this.favoriteRecipes = favoriteRecipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        // Bind first cell
        if (position * 2 < favoriteRecipes.size()) {
            Recipe recipe1 = favoriteRecipes.get(position * 2);
            holder.tvTitle1.setText(recipe1.getTitle());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
            Glide.with(context)
                    .load(recipe1.getImage())
                    .apply(requestOptions)
                    .into(holder.ivImage1);
        }

        // Bind second cell if exists
        if ((position * 2 + 1) < favoriteRecipes.size()) {
            Recipe recipe2 = favoriteRecipes.get(position * 2 + 1);
            holder.tvTitle2.setText(recipe2.getTitle());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
            Glide.with(context)
                    .load(recipe2.getImage())
                    .apply(requestOptions)
                    .into(holder.ivImage2);
        } else {
            // Hide second cell if not available
            holder.itemView.findViewById(R.id.tvTitle2).setVisibility(View.INVISIBLE);
            holder.itemView.findViewById(R.id.ivImage2).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(favoriteRecipes.size() / 2.0);
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle1, tvTitle2;
        ImageView ivImage1, ivImage2;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle1 = itemView.findViewById(R.id.tvTitle1);
            tvTitle2 = itemView.findViewById(R.id.tvTitle2);
            ivImage1 = itemView.findViewById(R.id.ivImage1);
            ivImage2 = itemView.findViewById(R.id.ivImage2);
        }
    }
}
