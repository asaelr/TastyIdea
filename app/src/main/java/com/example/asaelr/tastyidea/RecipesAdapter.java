package com.example.asaelr.tastyidea;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.example.asaelr.tastyidea.R;

import java.util.List;

import networking.RecipeMetadata;

public class RecipesAdapter extends ArrayAdapter<RecipeMetadata> {
    private final Context context;

    public RecipesAdapter(Context context, List<RecipeMetadata> list) {
        super(context, R.layout.row_recipe, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //if (convertView!=null) return convertView; //check this line...
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_recipe, parent, false);
        RecipeMetadata rmd = getItem(position);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.pic);
        ProgressBar progressBar = null;//(ProgressBar) rowView.findViewById(R.id.pic_loading);
//        new ImageDownloader(rmd.id, imageView, progressBar);
       // imageView.setImageResource(R.drawable.logo_small); //for now

        TextView recipe_name = (TextView) rowView.findViewById(R.id.name);
        recipe_name.setText(rmd.name);

        TextView time = (TextView) rowView.findViewById(R.id.time);
        time.setText(rmd.prepTimeMinutes+" minutes");

        TextView difficulty = (TextView) rowView.findViewById(R.id.difficulty);
        difficulty.setText(context.getResources().getStringArray(R.array.DifficultyLevel)[rmd.difficulty]);

        TextView category = (TextView) rowView.findViewById(R.id.category);
        category.setText(rmd.category);

//        RatingBar rating = (RatingBar)rowView.findViewById(R.id.rate);

        RatingBar rating = (RatingBar) rowView.findViewById(R.id.rate);
        LayerDrawable layerDrawable = (LayerDrawable) rating.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), ContextCompat.getColor(getContext(), R.color.colorAccent));  // Full star

        rating.setProgress(rmd.rate);

        return rowView;
    }
}