package com.example.lemmecook_frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemmecook_frontend.R;

import java.util.List;

public class IngredientOptionAdapter extends  RecyclerView.Adapter<IngredientOptionAdapter.ButtonViewHolder> {
    private List<String> buttonList;

    public IngredientOptionAdapter(List<String> buttonList) {
        this.buttonList = buttonList;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_button_item, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        String buttonText = buttonList.get(position);
        holder.button.setText(buttonText);

    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonItem);
        }
    }
}
