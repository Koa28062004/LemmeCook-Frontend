package com.example.lemmecook_frontend.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.lemmecook_frontend.R;
import com.example.lemmecook_frontend.activities.MainActivity;
import com.example.lemmecook_frontend.models.recipe.Recipe;
import com.example.lemmecook_frontend.models.viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFoodAdapter extends RecyclerView.Adapter<ViewPagerFoodAdapter.ViewHolder> {
    private List<List<Recipe>> chunkedItems;
    private Context context;

    public ViewPagerFoodAdapter(Context context, List<Recipe> items) {
        this.chunkedItems = chunkItems(items, 4);
        this.context = context;
    }

    private List<List<Recipe>> chunkItems(List<Recipe> items, int chunkSize) {
        List<List<Recipe>> chunks = new ArrayList<>();
        for (int i = 0; i < items.size(); i += chunkSize) {
            chunks.add(items.subList(i, Math.min(i + chunkSize, items.size())));
        }
        return chunks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Recipe> chunk = chunkedItems.get(position);

        // Bind data for each recipe in the chunk
        bindRecipe(holder.textView1, holder.imageView1, chunk, 0);
        bindRecipe(holder.textView2, holder.imageView2, chunk, 1);
        bindRecipe(holder.textView3, holder.imageView3, chunk, 2);
        bindRecipe(holder.textView4, holder.imageView4, chunk, 3);

        for (int i = 0; i < holder.llList.size(); i++) {
            LinearLayout l = holder.llList.get(i);
            int index = i; // capture the correct index for this loop iteration
            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Recipe recipeChosenByUser = chunk.get(index);

                    Log.d("ViewPagerFoodAdapter", "recipe chosen: " + recipeChosenByUser.getId());

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("startDestination", "RecipeOverviewScreen");
                    intent.putExtra("recipeID", recipeChosenByUser.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private void bindRecipe(TextView titleView, ImageView imageView, List<Recipe> chunk, int index) {
        if (index < chunk.size()) {
            Recipe recipe = chunk.get(index);
            titleView.setText(recipe.getTitle());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
            Glide.with(imageView.getContext())
                    .load(recipe.getImage())
                    .apply(requestOptions)
                    .into(imageView);
        } else {
            titleView.setText("");
            imageView.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return chunkedItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textView4;
        ImageView imageView1, imageView2, imageView3, imageView4;
        LinearLayout ll1, ll2, ll3, ll4;
        private List<LinearLayout> llList = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.tv1);
            textView2 = itemView.findViewById(R.id.tv2);
            textView3 = itemView.findViewById(R.id.tv3);
            textView4 = itemView.findViewById(R.id.tv4);
            imageView1 = itemView.findViewById(R.id.img1);
            imageView2 = itemView.findViewById(R.id.img2);
            imageView3 = itemView.findViewById(R.id.img3);
            imageView4 = itemView.findViewById(R.id.img4);

            ll1 = itemView.findViewById(R.id.ll1);
            ll2 = itemView.findViewById(R.id.ll2);
            ll3 = itemView.findViewById(R.id.ll3);
            ll4 = itemView.findViewById(R.id.ll4);
            llList.add(ll1);
            llList.add(ll2);
            llList.add(ll3);
            llList.add(ll4);
        }
    }
}
