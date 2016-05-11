package com.example.asaelr.tastyidea;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asaelr on 4/11/2016.
 */
public class IngCategory {
    public static Resources resources;
    public static List<IngCategory> categories;
    public static List<Ingredient> allIngredients;

    public List<Ingredient> ingredients=new ArrayList<Ingredient>();
    public String name;
    public int picture;

    public IngCategory(TypedArray ta) {
        name=ta.getString(0);
        picture=ta.getResourceId(1, 0);
        TypedArray tb = resources.obtainTypedArray(ta.getResourceId(2,0));
        for (int i=0;i<tb.length();i++) {
            int id = tb.getResourceId(i, 0);
            ingredients.add(new Ingredient(resources.obtainTypedArray(id),resources.getResourceName(id)));
        }
        tb.recycle();

        ta.recycle();
    }

    public static void initialize(Context context) {
        resources = context.getResources();
        categories = new ArrayList<IngCategory>();
        TypedArray ta = resources.obtainTypedArray(R.array.categories);
        for (int i=0;i<ta.length();i++) {
            categories.add(new IngCategory(resources.obtainTypedArray(ta.getResourceId(i, 0))));
        }
        ta.recycle();

        allIngredients = new ArrayList<Ingredient>();
        for (IngCategory cat : categories) allIngredients.addAll(cat.ingredients);
    }

    public static Ingredient getIngredient(String str) {
        for (Ingredient ing : allIngredients) {
            if (ing.nameOnServer.equals(str)) return ing;
        }
        return null;
    }
}
