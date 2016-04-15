package com.example.asaelr.tastyidea;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RecipeViewActivity extends AppCompatActivity {
    TextView recipe_name;
    ImageView recipe_pic;
    RatingBar rating;
    int ratingValue;
    TextView time;
    TextView difficulty;
    TextView category;
    TextView ingredients;
    TextView how_to_make;
    TextView comments;
    ImageButton my_recipes;
    ImageButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipe_name = (TextView) findViewById(R.id.recipe_name);
        recipe_pic = (ImageView) findViewById(R.id.pic);
        rating = (RatingBar) findViewById(R.id.rate);
        time = (TextView) findViewById(R.id.time);
        difficulty = (TextView) findViewById(R.id.difficulty);
        category = (TextView) findViewById(R.id.category);

        ingredients = (TextView) findViewById(R.id.ingredients);
        how_to_make = (TextView) findViewById(R.id.how_to_make);
        comments = (TextView) findViewById(R.id.comments);
        my_recipes = (ImageButton) findViewById(R.id.my_recipes);
        edit = (ImageButton) findViewById(R.id.edit);

        String chosen_recipe =  getIntent().getExtras().getString("chosen_recipe");


        //recipe_name.setText(getIntent().getExtras().getString("_recipe"));

        //TODO:
        int drawable_int = Integer.parseInt(getIntent().getExtras().getString("recipe_picture"));
        recipe_pic.setImageResource(drawable_int);


        int rating_val = Integer.parseInt(getIntent().getExtras().getString("rating"));
        rating.setRating(rating_val);
        time.setText(getIntent().getExtras().getString("time"));
        difficulty.setText(getIntent().getExtras().getString("difficulty"));
        category.setText(getIntent().getExtras().getString("category"));

        //TODO: change Later on

    }

}