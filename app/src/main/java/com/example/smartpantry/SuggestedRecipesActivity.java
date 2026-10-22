package com.example.smartpantry;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView rvRecipes;
    private TextView tvRecipeMessage;
    private DatabaseHelper databaseHelper;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        rvRecipes = findViewById(R.id.rvRecipes);
        tvRecipeMessage = findViewById(R.id.tvRecipeMessage);

        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        loadSuggestedRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {
        List<Ingredient> pantryIngredients = new ArrayList<>();

        Cursor cursor = databaseHelper.getAllIngredients();

        try {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(
                            cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
                    String name = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME));
                    double quantity = cursor.getDouble(
                            cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUANTITY));
                    String unit = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseHelper.COL_UNIT));
                    String expiry = cursor.getString(
                            cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EXPIRY));

                    pantryIngredients.add(
                            new Ingredient(id, name, quantity, unit, expiry));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        List<Recipe> availableRecipes = new ArrayList<>();

        for (Recipe recipe : getRecipes()) {
            if (canMakeRecipe(recipe, pantryIngredients)) {
                availableRecipes.add(recipe);
            }
        }

        if (availableRecipes.isEmpty()) {
            tvRecipeMessage.setVisibility(View.VISIBLE);
            tvRecipeMessage.setText(
                    "No matching recipes found. Check your ingredient names, " +
                            "units and quantities."
            );
            rvRecipes.setVisibility(View.GONE);
            return;
        }

        tvRecipeMessage.setVisibility(View.VISIBLE);
        tvRecipeMessage.setText(
                "You can make " + availableRecipes.size() +
                        (availableRecipes.size() == 1 ? " recipe." : " recipes.")
        );

        rvRecipes.setVisibility(View.VISIBLE);

        recipeAdapter = new RecipeAdapter(new ArrayList<>(availableRecipes));
        rvRecipes.setAdapter(recipeAdapter);

        rvRecipes.setAdapter(recipeAdapter);
    }

    private boolean canMakeRecipe(
            Recipe recipe, List<Ingredient> pantryIngredients) {

        for (RecipeIngredient required : recipe.getIngredients()) {
            double availableQuantity = 0;
            boolean found = false;

            for (Ingredient pantry : pantryIngredients) {
                if (pantry.getName().trim().equalsIgnoreCase(
                        required.getName().trim())
                        && pantry.getUnit().trim().equalsIgnoreCase(
                        required.getUnit().trim())) {

                    availableQuantity += pantry.getQuantity();
                    found = true;
                }
            }

            if (!found || availableQuantity < required.getQuantity()) {
                return false;
            }
        }

        return !recipe.getIngredients().isEmpty();
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe("Scrambled Eggs", "A quick egg breakfast.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Eggs", 2, "pieces"),
                        new RecipeIngredient("Milk", 30, "ml"),
                        new RecipeIngredient("Butter", 10, "g")
                )),
                "1. Beat the eggs with milk.\n2. Melt butter in a pan.\n3. Cook the eggs while stirring."));

        recipes.add(new Recipe("Pancakes", "Soft homemade pancakes.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Flour", 200, "g"),
                        new RecipeIngredient("Milk", 250, "ml"),
                        new RecipeIngredient("Eggs", 2, "pieces"),
                        new RecipeIngredient("Sugar", 20, "g")
                )),
                "1. Mix all ingredients into a batter.\n2. Pour into a hot pan.\n3. Cook both sides until golden."));

        recipes.add(new Recipe("French Toast", "A simple breakfast favourite.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Bread", 2, "slices"),
                        new RecipeIngredient("Eggs", 1, "pieces"),
                        new RecipeIngredient("Milk", 50, "ml")
                )),
                "1. Beat eggs and milk.\n2. Dip bread in the mixture.\n3. Fry both sides until golden."));

        recipes.add(new Recipe("Cheese Omelette", "A cheesy egg dish.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Eggs", 2, "pieces"),
                        new RecipeIngredient("Cheese", 30, "g"),
                        new RecipeIngredient("Butter", 10, "g")
                )),
                "1. Beat the eggs.\n2. Melt butter in a pan.\n3. Cook the eggs and add cheese."));

        recipes.add(new Recipe("Tomato Pasta", "Pasta with tomato sauce.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Pasta", 200, "g"),
                        new RecipeIngredient("Tomatoes", 200, "g"),
                        new RecipeIngredient("Onion", 1, "pieces"),
                        new RecipeIngredient("Garlic", 2, "pieces")
                )),
                "1. Cook pasta.\n2. Fry onion and garlic.\n3. Add tomatoes and simmer, then mix with pasta."));

        recipes.add(new Recipe("Garlic Bread", "Toasted bread with garlic butter.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Bread", 4, "slices"),
                        new RecipeIngredient("Butter", 30, "g"),
                        new RecipeIngredient("Garlic", 2, "pieces")
                )),
                "1. Mix butter and crushed garlic.\n2. Spread on bread.\n3. Toast until golden."));

        recipes.add(new Recipe("Banana Smoothie", "A quick fruit smoothie.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Banana", 1, "pieces"),
                        new RecipeIngredient("Milk", 250, "ml")
                )),
                "1. Peel the banana.\n2. Blend with milk until smooth."));

        recipes.add(new Recipe("Oatmeal", "Warm oats for breakfast.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Oats", 50, "g"),
                        new RecipeIngredient("Milk", 250, "ml"),
                        new RecipeIngredient("Sugar", 10, "g")
                )),
                "1. Add oats and milk to a pot.\n2. Simmer while stirring.\n3. Add sugar."));

        recipes.add(new Recipe("Mashed Potatoes", "Creamy mashed potatoes.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Potatoes", 300, "g"),
                        new RecipeIngredient("Butter", 20, "g"),
                        new RecipeIngredient("Milk", 50, "ml")
                )),
                "1. Boil potatoes until soft.\n2. Drain and mash.\n3. Mix in butter and milk."));

        recipes.add(new Recipe("Tomato Sandwich", "A fresh and simple sandwich.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Bread", 2, "slices"),
                        new RecipeIngredient("Tomatoes", 1, "pieces")
                )),
                "1. Slice the tomatoes.\n2. Place them on the bread and serve."));

        recipes.add(new Recipe("Fried Rice", "A simple egg fried rice.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Rice", 200, "g"),
                        new RecipeIngredient("Eggs", 2, "pieces"),
                        new RecipeIngredient("Carrot", 1, "pieces"),
                        new RecipeIngredient("Onion", 1, "pieces")
                )),
                "1. Cook the rice.\n2. Fry onion and carrot.\n3. Add eggs and rice, then stir-fry."));

        recipes.add(new Recipe("Vegetable Soup", "A warm vegetable soup.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Potatoes", 200, "g"),
                        new RecipeIngredient("Carrot", 2, "pieces"),
                        new RecipeIngredient("Onion", 1, "pieces")
                )),
                "1. Chop the vegetables.\n2. Add to a pot of water.\n3. Simmer until soft."));

        recipes.add(new Recipe("Chicken Rice", "Chicken served with rice.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Chicken", 200, "g"),
                        new RecipeIngredient("Rice", 200, "g"),
                        new RecipeIngredient("Onion", 1, "pieces")
                )),
                "1. Cook the rice.\n2. Cook the chicken thoroughly with onion.\n3. Serve together."));

        recipes.add(new Recipe("Tuna Sandwich", "A quick tuna sandwich.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Bread", 2, "slices"),
                        new RecipeIngredient("Tuna", 1, "can"),
                        new RecipeIngredient("Mayonnaise", 15, "g")
                )),
                "1. Mix tuna and mayonnaise.\n2. Spread on bread and serve."));

        recipes.add(new Recipe("Apple Oatmeal", "Oats with fresh apple.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Oats", 50, "g"),
                        new RecipeIngredient("Milk", 200, "ml"),
                        new RecipeIngredient("Apple", 1, "pieces")
                )),
                "1. Cook oats with milk.\n2. Chop the apple and add before serving."));

        recipes.add(new Recipe("Cheese Pasta", "Creamy cheese pasta.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Pasta", 200, "g"),
                        new RecipeIngredient("Cheese", 60, "g"),
                        new RecipeIngredient("Milk", 100, "ml")
                )),
                "1. Cook pasta.\n2. Warm milk and melt in cheese.\n3. Mix with pasta."));

        recipes.add(new Recipe("Potato Egg Hash", "A simple potato and egg meal.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Potatoes", 250, "g"),
                        new RecipeIngredient("Eggs", 2, "pieces"),
                        new RecipeIngredient("Onion", 1, "pieces")
                )),
                "1. Dice and cook the potatoes.\n2. Add onion and eggs.\n3. Cook until the eggs are set."));

        recipes.add(new Recipe("Carrot Salad", "A fresh grated carrot salad.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Carrot", 2, "pieces"),
                        new RecipeIngredient("Lemon", 1, "pieces")
                )),
                "1. Grate the carrots.\n2. Add lemon juice and mix."));

        recipes.add(new Recipe("Peanut Butter Toast", "A quick snack.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Bread", 2, "slices"),
                        new RecipeIngredient("Peanut Butter", 30, "g")
                )),
                "1. Toast the bread.\n2. Spread peanut butter on top."));

        recipes.add(new Recipe("Simple Rice Pudding", "A sweet rice dessert.",
                new ArrayList<>(Arrays.asList(
                        new RecipeIngredient("Rice", 100, "g"),
                        new RecipeIngredient("Milk", 300, "ml"),
                        new RecipeIngredient("Sugar", 20, "g")
                )),
                "1. Combine rice and milk in a pot.\n2. Simmer gently until the rice softens.\n3. Stir in sugar."));

        return recipes;
    }
}
