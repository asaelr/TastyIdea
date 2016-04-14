package com.example.asaelr.tastyidea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IngCategory.initialize(this);
        setContentView(R.layout.activity_search);
        //Log.e("TastyIdea", "" + getSupportActionBar());
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }
/*
    public void openDialog(View v) {
        IngredientAdder adder = new IngredientAdder() {
            @Override
            public void addIngredient(Ingredient ing) {
                //TypedArray ta = getResources().obtainTypedArray(id);
                //Toast.makeText(getApplicationContext(),"Select food "+ta.getString(0),Toast.LENGTH_SHORT).show();
                //ta.recycle();
                Toast.makeText(getApplicationContext(),"Select food "+ing.name,Toast.LENGTH_SHORT).show();
            }
        };
        CategorySelector is = new CategorySelector(adder);
        is.show(getFragmentManager(),"ingredient selector");
    }*/
}
