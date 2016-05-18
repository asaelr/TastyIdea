package com.example.asaelr.tastyidea;

import android.net.Network;
import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import networking.RecipeData;

/**
 * Created by osher on 5/9/2016.
 */
public class Recipe implements Serializable {
    private String name;
    private List<Pair<Ingredient,String>> ingredients;
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
        for (RecipeData.Ing ing : response.ingredients) {
            Ingredient i = IngCategory.getIngredient(ing.name);
            if (i==null) Log.e("TastyIdea","unknown ingredient "+ing.name);
            ingredients.add(new Pair<>(i,ing.amount));
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

    //use it to test logs
    @Override
    public String toString() {
        String str = "Recipe "+name+" of category "+category+" (by "+username+")\n";
        if (kosher) str+="kosher, ";
        if (vegeterian) str+="vegeterian, ";
        if (vegan) str+="vegan, ";
        str+="difficulty: "+difficulty+", rate: "+rate+", preptime: "+prepTimeMinutes+" minutes\n";
        str+="ingredients: ";
        for (Pair<Ingredient,String> p : ingredients) {
            str+=p.first.name+" ("+p.second+"), ";
        }
        str+="\ndirections:\n";
        for (String d : directions) str+=d+"\n";
        return str;
    }

    public List<Pair<Ingredient,String>> getIngredients() {
        return ingredients;
    }
}
