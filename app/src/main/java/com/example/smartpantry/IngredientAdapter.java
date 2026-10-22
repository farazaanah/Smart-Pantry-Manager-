package com.example.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final List<Ingredient> ingredients;
    private final OnIngredientActionListener listener;

    public interface OnIngredientActionListener {
        void onEdit(Ingredient ingredient);
        void onDelete(Ingredient ingredient);
    }

    public IngredientAdapter(List<Ingredient> ingredients,
                             OnIngredientActionListener listener) {
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.tvName.setText(ingredient.getName());
        holder.tvQuantity.setText(
                "Quantity: " + ingredient.getQuantity() + " " + ingredient.getUnit()
        );

        String expiry = ingredient.getExpiryDate();

        if (expiry == null || expiry.trim().isEmpty()) {
            holder.tvExpiry.setText("Expiry date: Not set");
        } else {
            holder.tvExpiry.setText("Expiry date: " + expiry);
        }

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(ingredient);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(ingredient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvQuantity, tvExpiry;
        Button btnEdit, btnDelete;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvIngredientName);
            tvQuantity = itemView.findViewById(R.id.tvIngredientQuantity);
            tvExpiry = itemView.findViewById(R.id.tvIngredientExpiry);
            btnEdit = itemView.findViewById(R.id.btnEditIngredient);
            btnDelete = itemView.findViewById(R.id.btnDeleteIngredient);
        }
    }
}

