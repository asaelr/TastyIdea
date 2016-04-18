package com.example.asaelr.tastyidea;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

    public class RecipesListActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
        private String selectedRecipe = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipes_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportFragmentManager().addOnBackStackChangedListener(this);
            if (findViewById(R.id.fragment_container) != null) {
                if (savedInstanceState != null) {
                    selectedRecipe = savedInstanceState.getString("chosen recipe");
                    return;
                }
                RecipesListActivityFragment recipeListFrag = new RecipesListActivityFragment();
                recipeListFrag.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, recipeListFrag).commit();
            } else {
                if (savedInstanceState != null) {
                    return;
                }

            }
        }

        @Override
        public void onBackStackChanged() {
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof RecipesListActivityFragment) {
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
