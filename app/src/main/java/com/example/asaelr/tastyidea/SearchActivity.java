package com.example.asaelr.tastyidea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import networking.Login;
import networking.Networking;


public class SearchActivity extends AppCompatActivity {

    //initialize some things in application, using context.
    //if we change our launcher activity, we shall move this function to the new one.
    private void initialize() {
        IngCategory.initialize(this);
        Networking.init(getApplicationContext());
        //Networking.ping();
        Networking.login();
        Login.init(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initialize();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TastyDrawerLayout.addDrawer(this, toolbar);

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
        Networking.get1();
    }

    public void login(View view) {
        Login.login(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Login.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Login.handleSignInResult(result);
        }
    }

    public void logout(View view) {
        Login.logout();
    }

    public void ping(View view) {
        Login.ping();
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