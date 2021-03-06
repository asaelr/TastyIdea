package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
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

    private static final String IS_EDIT_TEXT = "IS_EDIT_TEXT";
    public static final String INGREDIENTS_KEY = "ingredients";
    private boolean showEditText;

    private ArrayAdapter<Ingredient> adapter;
    private ArrayList<Ingredient> ingredients=new ArrayList<Ingredient>();
//    private ViewGroup[] sug = new ViewGroup[3];

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
       Log.i("ingredientsList","init");
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.IngredientList, defStyle, 0);
/*
        mExampleString = a.getString(
                R.styleable.IngredientList_exampleString);
*/
        setSaveEnabled(true);
        showEditText = a.getBoolean(R.styleable.IngredientList_showEditText, true);

        a.recycle();

        if (showEditText) {
            inflate(getContext(), R.layout.ingredient_list, this);
            adapter = new MyAdapterNF(getContext(), R.layout.ingredient_edittext, this);
            ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
        } else {
            inflate(getContext(), R.layout.ingredient_grid, this);
            adapter = new MyAdapterNF(getContext(), R.layout.ingredient_button, this);
            ((GridView) findViewById(R.id.gridView)).setAdapter(adapter);
        }
        //Log.e("TastyIdea","showEditText: "+showEditText);

        ((ImageButton) findViewById(R.id.imageButton)).setOnClickListener(new OnClickListener() {
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
        ((ImageButton) findViewById(R.id.clearButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ingredients.isEmpty()) return;
                new AlertDialog.Builder(getContext())
                        .setMessage(R.string.delete_confirmation)
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(R.string.delete_confirmation_positive_btn, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ingredients.clear();
                                adapter.clear();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.delete_confirmation_negative_btn, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        });
        List<String> ings = new ArrayList<>();
        for (Ingredient ing : IngCategory.allIngredients) ings.add(ing.name);
        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ings));
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ingredient ing = IngCategory.getIngredientByName("" + actv.getText());
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
/*
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
*/
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
//        //dummy!!!
//        int k=0;
//        for (Ingredient ing : IngCategory.allIngredients) {
//            if (k>=3) break;
//            if (ingredients.contains(ing)) continue;
//            ImageView imageView = (ImageView) sug[k].findViewById(R.id.imageView);
//            TextView textView = (TextView) sug[k].findViewById(R.id.textView);
//            textView.setText(ing.name);
//            imageView.setImageResource(ing.picture);
//            sug[k].setTag(ing);
//            k++;
//        }
//        for (;k<3;k++) {
//            sug[k].setTag(null);
//            ImageView imageView = (ImageView) sug[k].findViewById(R.id.imageView);
//            TextView textView = (TextView) sug[k].findViewById(R.id.textView);
//            textView.setText("");
//            imageView.setImageResource(android.R.drawable.stat_sys_warning);
//        }
    }


    public List<Ingredient> getList() {
        return ingredients;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Log.i("ingredientsList", "onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putSerializable(INGREDIENTS_KEY,ingredients);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Log.i("ingredientsList", "onRestoreInstanceState");
        if (state instanceof Bundle) // implicit null check
        {
            Log.i("ingredientsList", "onRestoreInstanceState if");
            Bundle bundle = (Bundle) state;
            this.ingredients = (ArrayList<Ingredient>) bundle.getSerializable(INGREDIENTS_KEY);
            adapter.addAll(ingredients);
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    class MyAdapterNF extends ArrayAdapter<Ingredient> {
        private final IngredientList ingList;
        private final int resource;

        public MyAdapterNF(Context context, int resource, IngredientList ingList) {
            super(context, resource);
            this.ingList = ingList;
            this.resource = resource;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
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
                public boolean onLongClick(final View v) {
                    new AlertDialog.Builder(ingList.getContext())
                            .setMessage(R.string.delete_confirmation)
                            .setIcon(android.R.drawable.ic_delete)
                            .setPositiveButton(R.string.delete_confirmation_positive_btn, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ingList.remove_ingredient((Ingredient) v.getTag());
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton(R.string.delete_confirmation_negative_btn, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    return true;
                }
            });
            return view;
        }
    }

}