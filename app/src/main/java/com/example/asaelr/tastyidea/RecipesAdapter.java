package com.example.asaelr.tastyidea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

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
         LayoutParams params = imageView.getLayoutParams();
        params.width = 230;
        params.height= 230;
        imageView.setLayoutParams(params);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        name.setText(names[position]);
        RatingBar rating = (RatingBar)rowView.findViewById(R.id.rate);
        TextView time = (TextView)rowView.findViewById(R.id.time);
        TextView difficulty = (TextView)rowView.findViewById(R.id.difficulty);
        TextView category = (TextView)rowView.findViewById(R.id.category);
        switch (position) {
            case 0:
                imageView.setImageResource(R.mipmap.ic_bolonez);
                rating.setProgress(2);
                time.setText("Time: 30 minutes");
                difficulty.setText("Difficulty: Easy");
                category.setText("Category: Italian");
                break;
            case 1:
                imageView.setImageResource(R.mipmap.ic_pancakes);
                rating.setProgress(3);
                time.setText("Time: 10 minutes");
                difficulty.setText("Difficulty: Avereage");
                category.setText("Category: Desserts");
                break;
            case 2:
                imageView.setImageResource(R.mipmap.ic_lazania);
                rating.setProgress(5);
                time.setText("Time: 1 hour");
                difficulty.setText("Difficulty: Average");
                category.setText("Category: Italian");
                break;
            case 3:
                imageView.setImageResource(R.mipmap.ic_tiras);
                rating.setProgress(1);
                time.setText("Time: 15 minutes");
                difficulty.setText("Difficulty: Easy");
                category.setText("Category: Pies");
                break;
            case 4:
                imageView.setImageResource(R.mipmap.ic_tootim);
                rating.setProgress(4);
                time.setText("Time: 75 minutes");
                difficulty.setText("Difficulty: Easy");
                category.setText("Category: Desserts");
                break;
        }
        return rowView;
    }
    String getName(int pos){
        return names[pos];
    }
}