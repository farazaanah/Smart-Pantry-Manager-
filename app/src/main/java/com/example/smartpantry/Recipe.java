package com.example.smartpantry;

import java.util.ArrayList;

public class Recipe {

    private final String name;
    private final String description;
    private final ArrayList<RecipeIngredient> ingredients;
    private final String instructions;

    public Recipe(String name, String description,
                  ArrayList<RecipeIngredient> ingredients,
                  String instructions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}

