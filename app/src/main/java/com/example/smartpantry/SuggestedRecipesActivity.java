package com.example.smartpantry;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView rvRecipes;
    private TextView tvRecipeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        databaseHelper = new DatabaseHelper(this);
        rvRecipes = findViewById(R.id.rvRecipes);
        tvRecipeMessage = findViewById(R.id.tvRecipeMessage);

        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes();
    }

    private void loadRecipes() {
        ArrayList<Ingredient> pantry = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllIngredients();

        try {
            while (cursor.moveToNext()) {
                pantry.add(new Ingredient(
                        cursor.getInt(cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_QUANTITY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_UNIT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_EXPIRY))
                ));
            }
        } finally {
            cursor.close();
        }

        ArrayList<Recipe> recipes = RecipeData.getRecipes();

        ArrayList<Recipe> availableRecipes =
                RecipeMatcher.getAvailableRecipes(recipes, pantry);

        if (availableRecipes.isEmpty()) {
            tvRecipeMessage.setText(
                    "No recipes available. Add the required ingredients " +
                            "to your pantry to see suggestions.");
        } else {
            tvRecipeMessage.setText(
                    "You can make " + availableRecipes.size() + " recipe(s)!");
        }

        rvRecipes.setAdapter(new RecipeAdapter(availableRecipes));
    }
}

