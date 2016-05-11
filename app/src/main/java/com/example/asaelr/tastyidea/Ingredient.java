package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.content.res.TypedArray;
import android.util.Log;

/**
 * Created by asaelr on 4/11/2016.
 */
public class Ingredient {
    public String name;
    public String nameOnServer;
    public int picture;

    public Ingredient(TypedArray ta, String resourceName) {
        nameOnServer = resourceName.split("array/")[1];
        name=ta.getString(0);
        picture=ta.getResourceId(1, 0);
        ta.recycle();

        Log.i("TastyIdea","new ingredient: "+nameOnServer+", "+name);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient)) return false;
        return nameOnServer.equals(((Ingredient) o).nameOnServer);
    }
}
