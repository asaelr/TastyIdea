package com.example.asaelr.tastyidea.SearchRecipes;

import com.example.asaelr.tastyidea.Ingredients.Ingredient;

import java.util.List;

/**
 * Created by Nati on 09/05/2016.
 */
public class SearchParams {
    private String category;
    private int maxPrepTimeMinutes;
    private int minRate;
    private int maxDifficulty;
    private boolean vegeterian;
    private boolean vegan;
    private boolean kosher;
    private List<Ingredient> ingredients;
    private List<Ingredient> excludedIngredients;
}
