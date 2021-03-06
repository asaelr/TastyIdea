package com.example.asaelr.tastyidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.asaelr.tastyidea.R;
import com.example.asaelr.tastyidea.TastyDrawerLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import networking.Login;

public class SettingsActivity extends AppCompatActivity  {
    private boolean isFromNavDrawer = false;
    private TastyDrawerLayout drawer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new PreferenceFragmentTastyIdea())
//                .commit();
        setContentView(R.layout.activity_settings);
        //TastyDrawerLayout.addDrawer(this, (Toolbar) findViewById(R.id.toolbar));

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        isFromNavDrawer = getIntent().getBooleanExtra(TastyDrawerLayout.FROM_DRAWER_KEY, false);
        Log.d("isFromNavDrawer", "********** settings is from nav = " + isFromNavDrawer);

        drawer = new TastyDrawerLayout(this,toolbar,new Login(this));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Login.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            drawer.handleLogin(result);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isFromNavDrawer) {startActivity(new Intent(this, SearchActivity.class));}
    }

}