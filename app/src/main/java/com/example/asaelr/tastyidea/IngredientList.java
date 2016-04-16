package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class IngredientList extends RelativeLayout {

    private boolean showEditText;

    private ArrayAdapter<Ingredient> adapter;
    private List<Ingredient> ingredients=new ArrayList<Ingredient>();
    private ViewGroup[] sug = new ViewGroup[3];

    public IngredientList(Context context) {
        super(context);
        if (!isInEditMode()) init(null, 0);
    }

    public IngredientList(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) init(attrs, 0);
    }

    public IngredientList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.IngredientList, defStyle, 0);
/*
        mExampleString = a.getString(
                R.styleable.IngredientList_exampleString);
*/

        showEditText = a.getBoolean(R.styleable.IngredientList_showEditText,true);

        a.recycle();
        inflate(getContext(), R.layout.ingredient_list, this);
        adapter=new MyAdapter(getContext(),
                showEditText?
                        R.layout.ingredient_edittext:R.layout.ingredient_button,
                this);
        Log.e("TastyIdea","showEditText: "+showEditText);
        //adapter.add(R.array.carrot);
        ((ListView)findViewById(R.id.listView)).setAdapter(adapter);

        ((ImageButton)findViewById(R.id.imageButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientAdder adder = new IngredientAdder() {
                    @Override
                    public void addIngredient(Ingredient ing) {

                        add_ingredient(ing);
                        //adapter.notifyDataSetChanged();
                    }
                };
                CategorySelector is = new CategorySelector(adder);
                is.show(((Activity) getContext()).getFragmentManager(), "ingredient selector");
            }
        });
        List<String> ings= new ArrayList<String>();
        for (Ingredient ing : IngCategory.allIngredients) ings.add(ing.name);
        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ings));
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ingredient ing = IngCategory.getIngredient("" + actv.getText());
                if (ing != null) {
                    add_ingredient(ing);
                    actv.setText("");
                }
            }
        });
        actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Ingredient ing = IngCategory.getIngredient("" + v.getText());
                if (ing != null) {
                    add_ingredient(ing);
                    v.setText("");
                }
                return false;
            }
        });

        sug[0]= (ViewGroup) findViewById(R.id.listframe1);
        sug[1]= (ViewGroup) findViewById(R.id.listframe2);
        sug[2]= (ViewGroup) findViewById(R.id.listframe3);

        for (View v : sug) {
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ingredient ing = (Ingredient) v.getTag();
                    if (ing!=null) add_ingredient(ing);
                }
            });
        }

        get_suggestions();
    }

    public void add_ingredient(Ingredient ing) {
        if (!ingredients.contains(ing)) {
            adapter.add(ing);
            ingredients.add(ing);
            get_suggestions();
        }
}

    public void remove_ingredient(Ingredient ing) {
        ingredients.remove(ing);
        adapter.remove(ing);
        get_suggestions();
    }

    public void get_suggestions() {
        //dummy!!!
        int k=0;
        for (Ingredient ing : IngCategory.allIngredients) {
            if (k>=3) break;
            if (ingredients.contains(ing)) continue;
            ImageView imageView = (ImageView) sug[k].findViewById(R.id.imageView);
            TextView textView = (TextView) sug[k].findViewById(R.id.textView);
            textView.setText(ing.name);
            imageView.setImageResource(ing.picture);
            sug[k].setTag(ing);
            k++;
        }
        for (;k<3;k++) {
            sug[k].setTag(null);
            ImageView imageView = (ImageView) sug[k].findViewById(R.id.imageView);
            TextView textView = (TextView) sug[k].findViewById(R.id.textView);
            textView.setText("");
            imageView.setImageResource(android.R.drawable.stat_sys_warning);
        }
    }
}

class MyAdapter extends ArrayAdapter<Ingredient> {
    private final IngredientList ingList;
    private final int resource;

    public MyAdapter(Context context, int resource, IngredientList ingList) {
        super(context, resource);
        this.ingList = ingList;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        // TypedArray typedArr = context.getResources().obtainTypedArray(arr.getIndex(position));
        //final int itemId = arr[position];
        Ingredient ing = getItem(position);
        textView.setText(ing.name);
        imageView.setImageResource(ing.picture);
        view.setTag(getItem(position));

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ingList.remove_ingredient((Ingredient) v.getTag());
                return true;
            }
        });
        return view;
    }
}