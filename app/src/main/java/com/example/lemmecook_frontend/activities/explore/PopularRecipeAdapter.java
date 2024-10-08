package com.example.lemmecook_frontend.activities.explore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.activities.MainActivity;
import com.example.lemmecook_frontend.models.recipe.Recipe;
import com.example.lemmecook_frontend.models.viewmodels.RecipeViewModel;

import java.util.List;

public class PopularRecipeAdapter extends RecyclerView.Adapter<PopularRecipeAdapter.ViewHolder>{
    private List<Recipe> recipeList;
    private Context context;
    private RecipeViewModel recipeViewModel;

    public PopularRecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popular_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.textView.setText(recipe.getTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context)
                .load(recipe.getImage()) // Replace with your image URL or resource
                .apply(requestOptions)
                .into(holder.imageView);

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipeChosenByUser = recipeList.get(position);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("startDestination", "RecipeOverviewScreen");
                intent.putExtra("recipeID", recipeChosenByUser.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textView = itemView.findViewById(R.id.item_text);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
