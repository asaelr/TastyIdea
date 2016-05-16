package com.example.asaelr.tastyidea;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.io.IOException;
import java.io.Serializable;

import networking.Networking;
import networking.RecipeMetadata;

/**
 * Created by asael on 16/05/16.
 */
public class RecipesSearcher implements Serializable, RecipesSupplier {

    public String[] ingredients;
    public boolean vegeterian = false;
    public boolean vegan = false;
    public boolean kosher = false;
    public int maxPrepTime = 500;
    public int minRating = 1;

    @Override
    public void supply(final ArrayAdapter<RecipeMetadata> adapter) {
        new AsyncTask<Void,Void,RecipeMetadata[]>() {

            @Override
            protected RecipeMetadata[] doInBackground(Void... params) {
                try {
                    SearchJSON search = new SearchJSON();
                    search.ingredients = ingredients;
                    search.vegeterian = vegeterian;
                    search.vegan = vegan;
                    search.kosher = kosher;
                    search.maxPrepTime = maxPrepTime;
                    search.minRating = minRating;
                    return Networking.searchRecipes(search);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(RecipeMetadata[] result) {
                adapter.addAll(result);
            }
        }.execute();
    }

    public static class SearchJSON extends GenericJson {
        @Key
        public String[] ingredients;
        @Key
        public boolean vegeterian = false;
        @Key
        public boolean vegan = false;
        @Key
        public boolean kosher = false;
        @Key
        public int maxPrepTime = 500;
        @Key
        public int minRating = 1;
    }
}
