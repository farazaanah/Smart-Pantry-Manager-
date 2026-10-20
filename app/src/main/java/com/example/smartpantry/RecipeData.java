package com.example.smartpantry;

import java.util.ArrayList;

public class RecipeData {

    public static ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe("Pancakes", "Homemade pancakes",
                ingredients("Milk", 250, "ml", "Flour", 200, "g",
                        "Eggs", 2, "pieces"),
                "Mix milk, flour and eggs. Cook both sides in a hot pan."));

        recipes.add(new Recipe("Scrambled Eggs", "Quick breakfast",
                ingredients("Eggs", 2, "pieces", "Milk", 30, "ml"),
                "Beat eggs and milk. Cook in a pan while stirring."));

        recipes.add(new Recipe("French Toast", "Egg-coated bread",
                ingredients("Bread", 2, "slices", "Eggs", 1, "pieces",
                        "Milk", 50, "ml"),
                "Dip bread in beaten egg and milk. Fry both sides."));

        recipes.add(new Recipe("Omelette", "Simple omelette",
                ingredients("Eggs", 2, "pieces", "Milk", 20, "ml"),
                "Beat eggs and milk. Cook in a pan until set."));

        recipes.add(new Recipe("Banana Smoothie", "Creamy fruit drink",
                ingredients("Banana", 1, "pieces", "Milk", 250, "ml"),
                "Blend banana and milk until smooth."));

        recipes.add(new Recipe("Boiled Eggs", "Basic boiled eggs",
                ingredients("Eggs", 2, "pieces"),
                "Boil eggs in water until cooked."));

        recipes.add(new Recipe("Flour Flatbread", "Quick flatbread",
                ingredients("Flour", 200, "g", "Water", 100, "ml"),
                "Mix flour and water. Flatten the dough and cook in a pan."));

        recipes.add(new Recipe("Banana Pancakes", "Banana and egg pancakes",
                ingredients("Banana", 1, "pieces", "Eggs", 2, "pieces"),
                "Mash banana, mix with eggs and cook small pancakes."));

        recipes.add(new Recipe("Milkshake", "Banana milkshake",
                ingredients("Milk", 250, "ml", "Banana", 1, "pieces"),
                "Blend milk and banana until smooth."));

        recipes.add(new Recipe("Egg Sandwich", "Simple egg sandwich",
                ingredients("Bread", 2, "slices", "Eggs", 1, "pieces"),
                "Cook the egg and place it between bread slices."));

        recipes.add(new Recipe("Toast", "Toasted bread",
                ingredients("Bread", 2, "slices"),
                "Toast bread until golden."));

        recipes.add(new Recipe("Scrambled Eggs on Toast", "Eggs on toast",
                ingredients("Eggs", 2, "pieces", "Bread", 2, "slices"),
                "Scramble eggs and serve with toasted bread."));

        recipes.add(new Recipe("Flour Porridge", "Warm breakfast porridge",
                ingredients("Flour", 50, "g", "Milk", 250, "ml"),
                "Whisk flour into milk and heat while stirring until thick."));

        recipes.add(new Recipe("Banana French Toast", "Fruit-topped toast",
                ingredients("Bread", 2, "slices", "Eggs", 1, "pieces",
                        "Milk", 50, "ml", "Banana", 1, "pieces"),
                "Dip bread in egg and milk, fry, then serve with banana."));

        recipes.add(new Recipe("Egg and Milk Scramble", "Creamy scrambled eggs",
                ingredients("Eggs", 3, "pieces", "Milk", 45, "ml"),
                "Beat eggs and milk. Cook slowly while stirring."));

        recipes.add(new Recipe("Banana Milk Bowl", "Banana with milk",
                ingredients("Banana", 2, "pieces", "Milk", 150, "ml"),
                "Slice bananas into a bowl and pour milk over them."));

        recipes.add(new Recipe("Plain Flour Pancakes", "Basic pancakes",
                ingredients("Flour", 150, "g", "Milk", 200, "ml",
                        "Eggs", 1, "pieces"),
                "Mix ingredients into batter and cook in a hot pan."));

        recipes.add(new Recipe("Egg Filling", "Cooked egg filling",
                ingredients("Eggs", 2, "pieces", "Milk", 15, "ml"),
                "Beat eggs and milk, then cook until set."));

        recipes.add(new Recipe("Banana Toast", "Toast topped with banana",
                ingredients("Bread", 2, "slices", "Banana", 1, "pieces"),
                "Toast bread and add sliced banana."));

        recipes.add(new Recipe("Bread Pudding", "Milk and egg bread pudding",
                ingredients("Bread", 2, "slices", "Milk", 200, "ml",
                        "Eggs", 1, "pieces"),
                "Combine torn bread, milk and egg. Bake until set."));

        recipes.add(new Recipe("Cereal with Milk", "Simple cereal breakfast",
                ingredients("Cereal", 50, "g", "Milk", 250, "ml"),
                "Place cereal in a bowl and add milk."));

        recipes.add(new Recipe("Sweetened Cereal", "Cereal with milk and sugar",
                ingredients("Cereal", 50, "g", "Milk", 250, "ml",
                        "Sugar", 5, "g"),
                "Add cereal to a bowl with milk and sprinkle with sugar."));

        recipes.add(new Recipe("Sweetened Milk", "Milk with sugar",
                ingredients("Milk", 250, "ml", "Sugar", 5, "g"),
                "Stir sugar into milk until dissolved."));

        return recipes;
    }

    private static ArrayList<RecipeIngredient> ingredients(
            Object... values) {
        ArrayList<RecipeIngredient> list = new ArrayList<>();

        for (int i = 0; i < values.length; i += 3) {
            list.add(new RecipeIngredient(
                    (String) values[i],
                    ((Number) values[i + 1]).doubleValue(),
                    (String) values[i + 2]));
        }

        return list;
    }
}

