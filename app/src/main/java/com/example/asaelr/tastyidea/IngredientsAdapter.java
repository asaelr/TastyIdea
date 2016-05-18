package com.example.asaelr.tastyidea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asaelr on 4/11/2016.
 */
public abstract class IngredientsAdapter extends BaseAdapter {
    private final List<Ingredient> ings;
    private Context context;

    public IngredientsAdapter(Context context, List<Ingredient> ings) {
        this.context=context;
        this.ings = ings;
    }

    @Override
    public int getCount() {
        return ings.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ingredient_button, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView textView = (TextView) view.findViewById(R.id.textView);

        final Ingredient ing = ings.get(position);
        textView.setText(ing.name);
        imageView.setImageResource(ing.picture);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(ing);
            }
        });
        return view;
    }

    public abstract void clicked(Ingredient ing);
}
