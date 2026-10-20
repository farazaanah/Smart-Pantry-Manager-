package com.example.smartpantry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView tvRecipeName;
    private TextView tvRecipeIngredients;
    private TextView tvRecipeInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        tvRecipeName = findViewById(R.id.tvRecipeName);
        tvRecipeIngredients = findViewById(R.id.tvRecipeIngredients);
        tvRecipeInstructions = findViewById(R.id.tvRecipeInstructions);

        String selectedName = getIntent().getStringExtra("recipe_name");

        Recipe selectedRecipe = null;
        ArrayList<Recipe> recipes = RecipeData.getRecipes();

        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(selectedName)) {
                selectedRecipe = recipe;
                break;
            }
        }

        if (selectedRecipe != null) {
            displayRecipe(selectedRecipe);
        } else {
            tvRecipeName.setText("Recipe not found");
            tvRecipeIngredients.setText("");
            tvRecipeInstructions.setText("");
        }
    }

    private void displayRecipe(Recipe recipe) {
        tvRecipeName.setText(recipe.getName());

        StringBuilder ingredientsText = new StringBuilder();

        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            ingredientsText.append("• ")
                    .append(ingredient.getName())
                    .append(" - ")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        tvRecipeIngredients.setText(ingredientsText.toString());
        tvRecipeInstructions.setText(recipe.getInstructions());
    }
}

