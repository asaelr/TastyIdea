package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.io.IOException;

import networking.Login;
import networking.Networking;

public class RecipesListActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, OnRecipeSelectedListener, LoginDataSupplier {
        private boolean isFromNavDrawer = false;

    private TastyDrawerLayout drawer;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Object obj = getIntent().getSerializableExtra(RecipesSupplier.SUPPLIER_KEY);
            Log.i("RecipesListActivity","supp type: "+obj.getClass().getName()+" val: "+obj);
            setContentView(R.layout.activity_recipes_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            RecipesSupplier supplier = (RecipesSupplier) getIntent().getSerializableExtra(RecipesSupplier.SUPPLIER_KEY);
            ((TextView)findViewById(R.id.toolbar_title)).setText(supplier.getTitle(getResources()));

            isFromNavDrawer = getIntent().getBooleanExtra(TastyDrawerLayout.FROM_DRAWER_KEY, false);
            Log.d("isFromNavDrawer", "********** recipes list is from nav = " + isFromNavDrawer);
            if(isFromNavDrawer)
            {
                drawer = new TastyDrawerLayout(this,toolbar,new Login(this));
            }
            getSupportFragmentManager().addOnBackStackChangedListener(this);
            if (findViewById(R.id.fragment_container) != null) {
                if (savedInstanceState != null) {
                    return;
                }
                RecipesListFragment recipeListFrag = new RecipesListFragment();
                recipeListFrag.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, recipeListFrag).commit();
            } else {
                if (savedInstanceState != null) {
                    return;
                }

            }
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

    @Override
    public void onBackStackChanged() {
//        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof RecipesListFragment) {
//            setTitle("Tasty Idea");
//        }
//        if (!selectedRecipe.equals("")) {
//            selectedRecipe = "";
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void handleRecipeSelected(final String recipeId) {
        new AsyncTask<String, Void, Recipe>(){
            @Override
            protected Recipe doInBackground(String... params) {
                try {
                    return Networking.getRecipe(recipeId);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Recipe recipe) {
                if (recipe!=null) setRecipe(recipe);
                else {
                    Toast.makeText(RecipesListActivity.this,R.string.cannot_load_recipe, Toast.LENGTH_LONG).show();
                }
            }
        }.execute();


    }

    private void setRecipe(Recipe selectedRecipe) {
        if (findViewById(R.id.fragment_container) != null) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            RecipeViewFragment recipeViewFragment;
            if (currentFragment instanceof RecipeViewFragment) {
                recipeViewFragment = (RecipeViewFragment) currentFragment;
            }
            else
            {
                recipeViewFragment = new RecipeViewFragment();
            }
            recipeViewFragment.setRecipe(selectedRecipe);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, recipeViewFragment).addToBackStack(null).commit();
        }
        else
        {
            ((RecipeViewFragment)getSupportFragmentManager().findFragmentById(R.id.recipe_view_frag)).setRecipe(selectedRecipe);
        }
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

    @Override
    public String getUserName() {
        return drawer.getUserName();
    }

    @Override
    public String getEmail() {
        return drawer.getEmail();
    }

    @Override
    public boolean isConnected() {
        return drawer.isConnected();
    }
}
