package com.example.asaelr.tastyidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

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
