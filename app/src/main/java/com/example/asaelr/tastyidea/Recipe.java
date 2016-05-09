package com.example.asaelr.tastyidea;

import android.util.Pair;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by osher on 5/9/2016.
 */
public class Recipe {
    private String name;
    private List<Pair<Ingredient,String>> ingredients;
    private List<String> directions;
    private String category;
    private int prepTimeMinutes;
    private int rate;
    private int difficulty;
    private boolean vegeterian;
    private boolean vegan;
    private boolean kosher;
    private String username;
    private String id;

    Recipe(String serverRecipeData){
        //TODO
        //ingredients=;
        //category=;
    }
}
