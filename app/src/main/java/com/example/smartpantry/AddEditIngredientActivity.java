package com.example.smartpantry;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName, etQuantity, etUnit, etExpiryDate;
    private DatabaseHelper databaseHelper;
    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        databaseHelper = new DatabaseHelper(this);

        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        Button btnSave = findViewById(R.id.btnSaveIngredient);

        ingredientId = getIntent().getIntExtra("ingredient_id", -1);

        if (ingredientId != -1) {
            etIngredientName.setText(
                    getIntent().getStringExtra("ingredient_name"));

            etQuantity.setText(String.valueOf(
                    getIntent().getDoubleExtra("ingredient_quantity", 0)));

            etUnit.setText(
                    getIntent().getStringExtra("ingredient_unit"));

            etExpiryDate.setText(
                    getIntent().getStringExtra("ingredient_expiry"));

            btnSave.setText("Update Ingredient");
        }

        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = etIngredientName.getText().toString().trim();
        String quantityText = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();

        if (TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(quantityText) ||
                TextUtils.isEmpty(unit)) {
            Toast.makeText(this,
                    "Please enter the name, quantity and unit",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);

            if (quantity <= 0) {
                Toast.makeText(this,
                        "Quantity must be greater than zero",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Please enter a valid quantity",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean saved;

        if (ingredientId == -1) {
            saved = databaseHelper.addIngredient(
                    name, quantity, unit, expiryDate);
        } else {
            saved = databaseHelper.updateIngredient(
                    ingredientId, name, quantity, unit, expiryDate);
        }

        if (saved) {
            Toast.makeText(this,
                    ingredientId == -1
                            ? "Ingredient saved"
                            : "Ingredient updated",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,
                    "Could not save ingredient",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

