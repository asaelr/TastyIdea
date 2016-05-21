package com.example.asaelr.tastyidea;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.asaelr.tastyidea.RecipesSupplier;
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
    public String[] excluded;
    public boolean vegeterian = false;
    public boolean vegan = false;
    public boolean kosher = false;
    public int maxPrepTime = 500;
    public int minRating = 1;
    public String category = ""; //empty string for "all categories"

    @Override
    public void supply(final Callback callback) {
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
                    search.category = category;
                    search.exclude = excluded;
                    return Networking.searchRecipes(search);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(RecipeMetadata[] result) {
                if (result!=null) callback.onSuccess(result);
                else callback.onFailure();
            }
        }.execute();
    }

    @Override
    public String getTitle(Resources res) {
        return res.getString(R.string.search_results);
    }

    public static class SearchJSON extends GenericJson {
        @Key
        public String[] ingredients;
        @Key
        public boolean vegeterian;
        @Key
        public boolean vegan;
        @Key
        public boolean kosher;
        @Key
        public int maxPrepTime;
        @Key
        public int minRating;
        @Key
        public String[] exclude;
        @Key
        public String category;
    }
}
