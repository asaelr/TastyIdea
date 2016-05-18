package com.example.asaelr.tastyidea;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class CategorySelector extends DialogFragment {

    private IngredientAdder adder;

    public CategorySelector() {
    }

        public CategorySelector(IngredientAdder adder) {
        super();
        this.adder = adder;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_ingredient_selector, null);
        builder.setView(view);

        GridView gridView = (GridView) view.findViewById(R.id.ingredients_grid);
        gridView.setAdapter(new adapter(getActivity(),R.array.categories) {

            @Override
            public void clicked(IngCategory cat) {
                dismiss();
                //adder.addIngredient(itemId);
                IngredientSelector is = new IngredientSelector(adder,cat);
                is.show(getFragmentManager(),"ingredient selector");
            }
        });
        return builder.create();
    }
}

abstract class adapter extends BaseAdapter {
    private Context context;

    public adapter(Context context, int arrId) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return IngCategory.categories.size();
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
        // TypedArray typedArr = context.getResources().obtainTypedArray(arr.getIndex(position));
        final IngCategory cat = IngCategory.categories.get(position);
        textView.setText(cat.name);
        imageView.setImageResource(cat.picture);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(cat);
            }
        });
        return view;
    }

    public abstract void clicked(IngCategory cat);
}
