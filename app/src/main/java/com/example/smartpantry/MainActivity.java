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
    private TextView tvIngredientCount;
    private IngredientAdapter adapter;
    private ArrayList<Ingredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        rvIngredients = findViewById(R.id.rvIngredients);
        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);
        tvIngredientCount = findViewById(R.id.tvIngredientCount);

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
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class);
            startActivity(intent);
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

        adapter = new IngredientAdapter(ingredients, new IngredientAdapter.OnIngredientActionListener() {
            @Override
            public void onEdit(Ingredient ingredient) {
                android.content.Intent intent = new android.content.Intent(
                        MainActivity.this, AddEditIngredientActivity.class);

                intent.putExtra("ingredient_id", ingredient.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Ingredient ingredient) {
                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Ingredient")
                        .setMessage("Are you sure you want to delete " + ingredient.getName() + "?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            databaseHelper.deleteIngredient(ingredient.getId());
                            loadIngredients();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        rvIngredients.setAdapter(adapter);

        int count = ingredients.size();

        tvIngredientCount.setText(
                count + (count == 1
                        ? " ingredient"
                        : " ingredients"));

        if (ingredients.isEmpty()) {
            tvEmptyPantry.setVisibility(View.VISIBLE);
            rvIngredients.setVisibility(View.GONE);
        } else {
            tvEmptyPantry.setVisibility(View.GONE);
            rvIngredients.setVisibility(View.VISIBLE);
        }
    }
}


