package com.example.lemmecook_frontend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemmecook_frontend.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterOptionAdapter extends RecyclerView.Adapter<FilterOptionAdapter.FilterOptionHolder>{
    private HashMap<String, String> filterOptionMap = new HashMap<>();
    private List<String> filterSearchByIngredient = new ArrayList<>();
    private List<String> buttonList = new ArrayList<>();
    private final Context context;

    // Constructor
    public FilterOptionAdapter(Context context, HashMap<String, String> filterOptionMap, List<String> filterSearchByIngredient) {
        this.context = context;
        this.filterOptionMap = filterOptionMap;
        this.filterSearchByIngredient = filterSearchByIngredient;

        for (String key : filterOptionMap.keySet()) {
            String value = filterOptionMap.get(key);
            if (!value.equals("All")) {
                buttonList.add(value);
            }
        }
        for (String i : filterSearchByIngredient) {
            buttonList.add(i);
        }
    }

    @NonNull
    @Override
    public FilterOptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_button_item, parent, false);
        return new FilterOptionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterOptionHolder holder, int position) {
        String buttonText = buttonList.get(position);
        holder.button.setText(buttonText);
        holder.button.setOnClickListener(v -> {
            // Handle button click here
        });
    }

    @Override
    public int getItemCount() {
        int n = filterSearchByIngredient.size();
        for (String key : filterOptionMap.keySet()) {
            String value = filterOptionMap.get(key);
            if (!value.equals("All")) {
                ++n;
            }
        }
        return n;
    }

    static class FilterOptionHolder extends RecyclerView.ViewHolder {
        Button button;

        public FilterOptionHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonItem);
        }
    }
}
