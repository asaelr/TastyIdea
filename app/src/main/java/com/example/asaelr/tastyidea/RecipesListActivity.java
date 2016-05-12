package com.example.asaelr.tastyidea;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

public class RecipesListActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipes_list, menu);
        return true;
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
    }
