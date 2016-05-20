package com.example.asaelr.tastyidea;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import networking.Login;
import networking.Networking;


public class SearchActivity extends AppCompatActivity implements ConfirmExitDialog.YesNoListener{
//    private static final int NUM_ITEMS = SearchPagerItem.values().length;
    private static AtomicBoolean isFirstActivityCreate = new AtomicBoolean(true);
    //initialize some things in application, using context.
    //if we change our launcher activity, we shall move this function to the new one.
    private void initialize() {
        Log.d("init", "************************initialize");
        IngCategory.initialize(this);
        Networking.init(getApplicationContext());
        //Networking.ping();
        Networking.login();
        //Login.init(this);
    }
    private TastyDrawerLayout drawer;
//    private SearchPagerAdapter mAdapter;
//    private ViewPager mPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(isFirstActivityCreate.getAndSet(false)) initialize();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        mAdapter = new SearchPagerAdapter(getFragmentManager());
//        mPager = (ViewPager)findViewById(R.id.pager);
//        mPager.setAdapter(mAdapter);

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
//        ((SearchFragment)mAdapter.getItem(SearchPagerItem.GENERAL.ordinal())).getChildFragmentManager().findFragmentById(R.id.ingredient_list_fragment)
        Log.i("SearchActivity","ings: "+ings);
        if(ings.isEmpty())
        {
            Toast.makeText(this,
                    getString(R.string.no_ingredients_selected_message), Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = new Intent(this,RecipesListActivity.class);
        RecipesSearcher search = new RecipesSearcher();
        search.ingredients = new String[ings.size()];
        int i=0;
        for (Ingredient ing : ings) search.ingredients[i++]=ing.nameOnServer;
        Spinner spinner = (Spinner) findViewById(R.id.categories_spinner);
        search.category =
                getResources().getStringArray(R.array.recipe_categories_serverName)[spinner.getSelectedItemPosition()];
        intent.putExtra(RecipesSupplier.SUPPLIER_KEY,search);
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


    @Override
    public void onBackPressed() {
        new ConfirmExitDialog().show(getFragmentManager(), "tag");
    }

    @Override
    public void onYes() {
        finish();
    }

    @Override
    public void onNo() {
        //do nothing
    }

//    public class SearchPagerAdapter extends FragmentPagerAdapter {
//        public SearchPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return NUM_ITEMS;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return SearchPagerItem.values()[position].newFragInstance();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return getApplicationContext().getString(SearchPagerItem.values()[position].getPageTitle());
//        }
//    }
//
//    private enum SearchPagerItem
//    {
//        GENERAL {
//            @Override
//            public Fragment newFragInstance() {
//                return new SearchFragment();
//            }
//
//            @Override
//            public int getPageTitle() {
//                return R.string.general;
//            }
//        },
//        ADVANCED_SETTINGS {
//            @Override
//            public Fragment newFragInstance() {
//                return new AdvancedSearchFragment();
//            }
//
//            @Override
//            public int getPageTitle() {
//                return R.string.advanced_settings;
//            }
//        };
//
//        public abstract Fragment newFragInstance();
//        public abstract int getPageTitle();
//    }
}