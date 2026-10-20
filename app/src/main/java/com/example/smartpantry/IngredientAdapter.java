package com.example.smartpantry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final ArrayList<Ingredient> ingredients;

    public IngredientAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder,
                                 int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.nameText.setText(ingredient.getName());
        holder.detailsText.setText(
                ingredient.getQuantity() + " " + ingredient.getUnit()
                        + " | Expiry: " + ingredient.getExpiryDate());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, AddEditIngredientActivity.class);
            intent.putExtra("ingredient_id", ingredient.getId());
            intent.putExtra("ingredient_name", ingredient.getName());
            intent.putExtra("ingredient_quantity", ingredient.getQuantity());
            intent.putExtra("ingredient_unit", ingredient.getUnit());
            intent.putExtra("ingredient_expiry", ingredient.getExpiryDate());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Ingredient")
                    .setMessage("Delete " + ingredient.getName() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        DatabaseHelper databaseHelper =
                                new DatabaseHelper(v.getContext());

                        boolean deleted = databaseHelper.deleteIngredient(
                                ingredient.getId());

                        if (deleted) {
                            int currentPosition =
                                    holder.getAdapterPosition();

                            if (currentPosition != RecyclerView.NO_POSITION) {
                                ingredients.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                            }

                            Toast.makeText(v.getContext(),
                                    "Ingredient deleted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(),
                                    "Could not delete ingredient",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView detailsText;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(android.R.id.text1);
            detailsText = itemView.findViewById(android.R.id.text2);
        }
    }
}


