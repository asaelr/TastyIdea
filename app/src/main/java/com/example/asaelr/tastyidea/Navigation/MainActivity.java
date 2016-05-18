package com.example.asaelr.tastyidea.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.asaelr.tastyidea.AddRecipe.AddRecepieActivity;
import com.example.asaelr.tastyidea.R;
import com.example.asaelr.tastyidea.RecipeView.RecipeViewActivity;
import com.example.asaelr.tastyidea.RecipesList.IngCategory;
import com.example.asaelr.tastyidea.RecipesList.RecipesListActivity;
import com.example.asaelr.tastyidea.SearchRecipes.SearchActivity;
import com.example.asaelr.tastyidea.Preferences.SettingsActivity;

import networking.Networking;

public class MainActivity extends AppCompatActivity {

    //initialize some things in application, using context.
    //if we change our launcher activity, we shall move this function to the new one.
    private void initialize() {
        IngCategory.initialize(this);
        Networking.init(getApplicationContext());
        //Networking.ping();
        Networking.login();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    public void handleSearchPressed(View view)
    {
        startActivity(new Intent(this, SearchActivity.class));
    }

    public void handleSettingsPressed(View view)
    {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void handleAddRecepiePressed(View view)
    {
        startActivity(new Intent(this, AddRecepieActivity.class));
    }
    public void showRecipesList(View view)
    {
        startActivity(new Intent(this, RecipesListActivity.class));
    }
    public void showRecipe(View view)
    {
        startActivity(new Intent(this, RecipeViewActivity.class));
    }
}
