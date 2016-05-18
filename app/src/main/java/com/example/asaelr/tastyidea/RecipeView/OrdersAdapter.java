package com.example.asaelr.tastyidea.RecipeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.example.asaelr.tastyidea.R;

public class OrdersAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] orders;

    public OrdersAdapter(Context context, String[] values) {
        super(context, R.layout.row_orders, values);
        this.context = context;
        this.orders = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_orders, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        name.setText(orders[position]);
        return rowView;
    }
    String getName(int pos){
        return orders[pos];
    }
}