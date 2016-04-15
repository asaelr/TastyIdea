package com.example.asaelr.tastyidea;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private String name;
    private Bitmap bitmap;
    private int rating;
    private String time;
    private String difficult;
    private String category;
    private List<String> ingredients;
    private String how_to_make;
    private String commmnets;

    public Recipe(String name,Bitmap bitmap,int rating,String time,String difficult,String category,
                  List<String> ingredients, String how_to_make, String commmnets){
        this.name = name;
        this.bitmap = bitmap;
        this.rating = rating;
        this.time = time;
        this.difficult=difficult;
        this.category=category;
        this.how_to_make=how_to_make;
        this.commmnets=commmnets;
        this.ingredients = new ArrayList<>();
        for (String ingredient : ingredients){
            this.ingredients.add(ingredient);
        }
    }
    public Recipe(String name,Bitmap bitmap,int rating,String time,String difficult,String category){
        this.name = name;
        this.bitmap = bitmap;
        this.rating = rating;
        this.time = time;
        this.difficult=difficult;
        this.category=category;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int describeContents() {

    }

    public static final Parcelable.Creator<Recipe> CREEATOR = new Parcelable.Creator<Recipe>(){
        @Override
        public Recipe createFromParcel(Parcel source) {

            return null;
        }

        @Override
        public Recipe[] newArray(int size) {

            return new Recipe[0];
        }
    };

}
