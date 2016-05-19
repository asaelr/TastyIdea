package com.example.asaelr.tastyidea;

import android.net.Network;
import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import networking.RecipeData;

/**
 * Created by osher on 5/9/2016.
 */
public class Recipe implements Serializable {
    private String name;
    private List<Ingredient> ingredients;
    private List<String> amounts;
    private String[] directions;
    private String category;
    private int prepTimeMinutes;
    private int rate;
    private int difficulty;
    private boolean vegeterian;
    private boolean vegan;
    private boolean kosher;
    private String username;
    private String id;

    public Recipe(RecipeData response){
        name = response.name;
        directions = response.directions;
        category = response.category;
        prepTimeMinutes = response.prepTimeMinutes;
        rate = response.rate;
        difficulty = response.difficulty;
        vegeterian = response.vegeterian;
        vegan = response.vegan;
        kosher = response.kosher;
        username = response.username;
        id = response.id;

        ingredients = new ArrayList<>();
        amounts = new ArrayList<>();
        for (RecipeData.Ing ing : response.ingredients) {
            Ingredient i = IngCategory.getIngredient(ing.name);
            if (i==null) Log.e("TastyIdea","unknown ingredient "+ing.name);
            ingredients.add(i);
            amounts.add(ing.amount);
        }

    }

    public String getName() {
        return name;
    }

    public String[] getDirections() {
        return directions;
    }

    public String getCategory() {
        return category;
    }

    public int getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public int getRate() {
        return rate;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isVegeterian() {
        return vegeterian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isKosher() {
        return kosher;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public List<Pair<Ingredient,String>> getIngredients() {
        List<Pair<Ingredient,String>> result = new ArrayList<>();
        Iterator<Ingredient> ing = ingredients.iterator();
        Iterator<String> am = amounts.iterator();
        while (ing.hasNext()) {
            result.add(new Pair<Ingredient, String>(ing.next(),am.next()));
        }
        return result;
    }
}
