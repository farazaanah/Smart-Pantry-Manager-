package com.example.smartpantry;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView rvIngredients;
    private TextView tvEmptyPantry;
    private IngredientAdapter adapter;
    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        rvIngredients = findViewById(R.id.rvIngredients);
        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);

        Button btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        Button btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);

        Button btnSettings =
                findViewById(R.id.btnSettings);

        rvIngredients.setLayoutManager(
                new LinearLayoutManager(this));

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class);
            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent recipeIntent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class);
            startActivity(recipeIntent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class);
            startActivity(settingsIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
    }

    private void loadIngredients() {
        ingredients = new ArrayList<>();

        Cursor cursor = databaseHelper.getAllIngredients();

        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_ID));

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_NAME));

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_QUANTITY));

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_UNIT));

                String expiryDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COL_EXPIRY));

                ingredients.add(new Ingredient(
                        id, name, quantity, unit, expiryDate));
            }
        } finally {
            cursor.close();
        }

        adapter = new IngredientAdapter(ingredients);
        rvIngredients.setAdapter(adapter);

        if (ingredients.isEmpty()) {
            tvEmptyPantry.setVisibility(View.VISIBLE);
            rvIngredients.setVisibility(View.GONE);
        } else {
            tvEmptyPantry.setVisibility(View.GONE);
            rvIngredients.setVisibility(View.VISIBLE);
        }
    }
}

