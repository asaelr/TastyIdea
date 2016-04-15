package com.example.asaelr.tastyidea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RecipesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] names;

    public RecipesAdapter(Context context, String[] values) {
        super(context, R.layout.row_recipe, values);
        this.context = context;
        this.names = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_recipe, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.pic);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        name.setText(names[position]);
        RatingBar rating = (RatingBar)rowView.findViewById(R.id.rate);
        TextView time = (TextView)rowView.findViewById(R.id.time);
        TextView difficulty = (TextView)rowView.findViewById(R.id.difficulty);
        TextView category = (TextView)rowView.findViewById(R.id.category);
        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.ic_action_bolonez);
                rating.setProgress(2);
                time.setText("30 minutes");
                difficulty.setText("Easy");
                category.setText("Cooking,Italian");
                break;
            case 1:
                imageView.setImageResource(R.drawable.ic_action_pancakes);
                rating.setProgress(3);
                time.setText("10 minutes");
                difficulty.setText("Medium");
                category.setText("Frying,Desserts,Vegetarian");
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_action_lazania);
                rating.setProgress(5);
                time.setText("1 hour");
                difficulty.setText("Medium");
                category.setText("Baking,Italian");
                break;
            case 3:
                imageView.setImageResource(R.drawable.ic_action_tiras);
                rating.setProgress(1);
                time.setText("15 minutes");
                difficulty.setText("Easy");
                category.setText("Baking,Vegetarian,Pies");
                break;
            case 4:
                imageView.setImageResource(R.drawable.ic_action_tootim);
                rating.setProgress(4);
                time.setText("1 hour and 15 minutes");
                difficulty.setText("Easy");
                category.setText("Cake,Vegetarian,Desserts,American");
                break;
        }
        return rowView;
    }
    String getName(int pos){
        return names[pos];
    }
}