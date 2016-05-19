package com.example.asaelr.tastyidea;

import android.content.res.Resources;
import android.widget.ArrayAdapter;

import networking.RecipeMetadata;

/**
 * Created by asael on 16/05/16.
 */
public interface RecipesSupplier {
    String SUPPLIER_KEY = "supplier";

    void supply(Callback callback);

    String getTitle(Resources res);

    public static interface Callback {
        void onSuccess(RecipeMetadata[] recipes);

        void onFailure();
    }
}
