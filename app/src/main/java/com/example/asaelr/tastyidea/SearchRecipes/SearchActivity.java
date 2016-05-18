package com.example.asaelr.tastyidea.SearchRecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.asaelr.tastyidea.Ingredients.Ingredient;
import com.example.asaelr.tastyidea.Ingredients.IngredientListFragment;
import com.example.asaelr.tastyidea.R;
import com.example.asaelr.tastyidea.RecipesList.IngCategory;
import com.example.asaelr.tastyidea.RecipesList.RecipesListActivity;
import com.example.asaelr.tastyidea.Navigation.TastyDrawerLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.util.List;

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
        //Login.init(this);
    }
    private TastyDrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initialize();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = new TastyDrawerLayout(this,toolbar,new Login(this));

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
        //Networking.get1();
        List<Ingredient> ings;
        ings=((IngredientListFragment)getFragmentManager().findFragmentById(R.id.ingredient_list_fragment)).getList();
        Log.i("SearchActivity","ings: "+ings);
        Intent intent = new Intent(this,RecipesListActivity.class);
        RecipesSearcher search = new RecipesSearcher();
        search.ingredients = new String[ings.size()];
        int i=0;
        for (Ingredient ing : ings) search.ingredients[i++]=ing.nameOnServer;
        intent.putExtra("supplier",search);
        Object obj = intent.getSerializableExtra("supplier");
        Log.i("SearchActivity","supp type: "+obj.getClass().getName()+" val: "+obj);
        startActivity(intent);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.search_settings) {
            //PreferenceFragmentTastyIdea pfti = new PreferenceFragmentTastyIdea();
            //getFragmentManager().beginTransaction().replace(R.id.search_fragment_container,pfti).addToBackStack(null).commit();
            SearchDialog sd = new SearchDialog(this,null);

            return true;
        }
        return false;
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