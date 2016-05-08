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
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class RecipesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] names;
    private String[] times;
    private String[] difficulties;
    private String[] categories;

    public RecipesAdapter(Context context, String[] names,String[] times,String[] difficulties,String[] categories) {
        super(context, R.layout.row_recipe, names);
        this.context = context;
        this.names = names;
        this.times=times;
        this.difficulties=difficulties;
        this.categories=categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_recipe, parent, false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.pic);
//         LayoutParams params = imageView.getLayoutParams();
//        params.width = 230;
//        params.height= 230;
//        imageView.setLayoutParams(params);

        TextView recipe_name = (TextView) rowView.findViewById(R.id.name);
        recipe_name.setText(names[position]);

        TextView time = (TextView) rowView.findViewById(R.id.time);
        time.setText(times[position]);

        TextView difficulty = (TextView) rowView.findViewById(R.id.difficulty);
        difficulty.setText(difficulties[position]);

        TextView category = (TextView) rowView.findViewById(R.id.category);
        category.setText(categories[position]);

//        RatingBar rating = (RatingBar)rowView.findViewById(R.id.rate);

        RatingBar rating = (RatingBar) rowView.findViewById(R.id.rate);
        LayerDrawable layerDrawable = (LayerDrawable) rating.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), ContextCompat.getColor(getContext(), R.color.colorAccent));  // Full star

        switch (position) {
            case 0:
                imageView.setImageResource(R.mipmap.ic_bolonez);
                rating.setProgress(2);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.ic_pancakes);
                rating.setProgress(3);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.ic_lazania);
                rating.setProgress(5);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.ic_tiras);
                rating.setProgress(1);
                break;
            case 4:
                imageView.setImageResource(R.mipmap.ic_tootim);
                rating.setProgress(4);
                break;
        }
        return rowView;
    }
}