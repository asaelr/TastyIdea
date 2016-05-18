package com.example.asaelr.tastyidea.Recipe;

import android.widget.ArrayAdapter;

import networking.RecipeMetadata;

/**
 * Created by asael on 16/05/16.
 */
public interface RecipesSupplier {
    void supply(Callback callback);

    public static interface Callback {
        void onSuccess(RecipeMetadata[] recipes);
    }
}
