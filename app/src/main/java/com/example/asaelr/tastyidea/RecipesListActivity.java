package com.example.asaelr.tastyidea;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import java.io.IOException;

import networking.Networking;

public class RecipesListActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, OnRecipeSelectedListener{
        private String selectedRecipe = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipes_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            TastyDrawerLayout.addDrawer(this, toolbar);


            getSupportFragmentManager().addOnBackStackChangedListener(this);
            if (findViewById(R.id.fragment_container) != null) {
                if (savedInstanceState != null) {
                    selectedRecipe = savedInstanceState.getString("chosen recipe");
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
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof RecipesListFragment) {
            setTitle("Tasty Idea");
        }
        if (!selectedRecipe.equals("")) {
            selectedRecipe = "";
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("chosen recipe", selectedRecipe);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRecipeSelected(String food) {
        if (findViewById(R.id.fragment_container) != null) {
            selectedRecipe = food;
        }
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
                }
                return null;
            }

            @Override
            protected void onPostExecute(Recipe recipe) {
                setRecipe(recipe);
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
}
