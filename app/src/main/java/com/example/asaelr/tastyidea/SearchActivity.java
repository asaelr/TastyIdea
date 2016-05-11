package com.example.asaelr.tastyidea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import networking.Networking;


public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar((Toolbar)findViewById(R.id.TOOLBAR));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Log.e("TastyIdea", "" + getSupportActionBar());
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //  getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    public void searchClick(View v) {
        Log.e("TastyIdea","search clicked!");
        Networking.init(getApplicationContext());
        Networking.ping();
        Networking.login();
        Networking.get1();
    }
    /* TODO
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.search_settings) {
            PreferenceFragmentTastyIdea pfti = new PreferenceFragmentTastyIdea();
            getFragmentManager().beginTransaction().replace(android.R.id.content,pfti).commit();
            return true;
        }
        return false;
    }*/
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