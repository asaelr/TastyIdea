package com.example.asaelr.tastyidea;

import android.content.res.Resources;

import java.io.Serializable;

/**
 * Created by Nati on 5/19/2016.
 */
public class MockSupplier implements Serializable, RecipesSupplier {
    @Override
    public void supply(Callback callback) {

    }

    @Override
    public String getTitle(Resources res) {
        return "MockSupplier";
    }
}
