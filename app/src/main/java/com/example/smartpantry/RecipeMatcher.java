package com.example.smartpantry;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeMatcher {

    public static ArrayList<Recipe> getAvailableRecipes(
            ArrayList<Recipe> recipes,
            ArrayList<Ingredient> pantry) {

        ArrayList<Recipe> availableRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            boolean allAvailable = true;

            for (RecipeIngredient required : recipe.getIngredients()) {
                boolean found = false;

                for (Ingredient item : pantry) {
                    boolean sameName = item.getName().trim()
                            .equalsIgnoreCase(required.getName().trim());

                    boolean sameUnit = item.getUnit().trim()
                            .equalsIgnoreCase(required.getUnit().trim());

                    if (sameName && sameUnit
                            && item.getQuantity() >= required.getQuantity()) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    allAvailable = false;
                    break;
                }
            }

            if (allAvailable) {
                availableRecipes.add(recipe);
            }
        }

        return availableRecipes;
    }
}

