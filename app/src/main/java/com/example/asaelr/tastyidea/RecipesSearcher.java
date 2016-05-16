package com.example.asaelr.tastyidea;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.io.IOException;

import networking.Networking;
import networking.RecipeMetadata;

/**
 * Created by asael on 16/05/16.
 */
public class RecipesSearcher extends GenericJson implements Parcelable, RecipesSupplier {

    private RecipeMetadata[] recipes = null;
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

    public RecipesSearcher() {}

    protected RecipesSearcher(Parcel in) {
        int rmdNum=in.readInt();
        if (rmdNum>0) {
            recipes = new RecipeMetadata[rmdNum];
            for (int i=0;i<rmdNum;i++) {
                recipes[i].category = in.readString();
                recipes[i].difficulty = in.readInt();
                recipes[i].id = in.readString();
                recipes[i].name = in.readString();
                recipes[i].prepTimeMinutes = in.readInt();
                recipes[i].rate = in.readInt();
            }
        }
        ingredients = in.createStringArray();
        vegeterian = (boolean) in.readValue(null);
        vegan = (boolean) in.readValue(null);
        kosher = (boolean) in.readValue(null);
        maxPrepTime = in.readInt();
        minRating = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (recipes==null) dest.writeInt(0);
        else {
            dest.writeInt(recipes.length);
            for (RecipeMetadata rmd : recipes) {
                dest.writeString(rmd.category);
                dest.writeInt(rmd.difficulty);
                dest.writeString(rmd.id);
                dest.writeString(rmd.name);
                dest.writeInt(rmd.prepTimeMinutes);
                dest.writeInt(rmd.rate);
            }
        }
        dest.writeStringArray(ingredients);
        dest.writeValue(vegeterian);
        dest.writeValue(vegan);
        dest.writeValue(kosher);
        dest.writeInt(maxPrepTime);
        dest.writeInt(minRating);
    }

    @Override
    public void supply(final ArrayAdapter<RecipeMetadata> adapter) {
        if (recipes!=null) {
            adapter.addAll(recipes);
            return;
        }
        new AsyncTask<Void,Void,RecipeMetadata[]>() {

            @Override
            protected RecipeMetadata[] doInBackground(Void... params) {
                try {
                    return Networking.searchRecipes(RecipesSearcher.this);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(RecipeMetadata[] result) {
                recipes=result;
                adapter.addAll(recipes);
            }
        }.execute();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipesSearcher> CREATOR = new Creator<RecipesSearcher>() {
        @Override
        public RecipesSearcher createFromParcel(Parcel in) {
            return new RecipesSearcher(in);
        }

        @Override
        public RecipesSearcher[] newArray(int size) {
            return new RecipesSearcher[size];
        }
    };
}
