package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.content.res.TypedArray;

/**
 * Created by asaelr on 4/11/2016.
 */
public class Ingredient {
    public String name;
    public int picture;

    public Ingredient(TypedArray ta) {
        name=ta.getString(0);
        picture=ta.getResourceId(1, 0);
        ta.recycle();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient)) return false;
        return name.equals(((Ingredient) o).name);
    }
}
