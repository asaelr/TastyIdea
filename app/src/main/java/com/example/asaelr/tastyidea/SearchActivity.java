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
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import networking.Login;
import networking.Networking;


public class SearchActivity extends AppCompatActivity implements ConfirmExitDialog.YesNoListener{
    private static final int NUM_ITEMS = SearchPagerItem.values().length;
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
    private SearchPagerAdapter mAdapter;
    private ViewPager mPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(isFirstActivityCreate.getAndSet(false)) initialize();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAdapter = new SearchPagerAdapter(getFragmentManager());
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);


        drawer = new TastyDrawerLayout(this,toolbar,new Login(this));

        //Log.e("TastyIdea", "" + getSupportActionBar());
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //  getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    public void searchClick(View v) {
        Log.e("TastyIdea","search clicked!");
        //Networking.get1();
        List<Ingredient> selectedIngs;
        selectedIngs=((SearchFragment)mAdapter.getFragmentAtPosition(SearchPagerItem.GENERAL.ordinal())).getSelectedIngredients();
        Log.i("SearchActivity","ings: "+selectedIngs);
        if(selectedIngs.isEmpty())
        {
            Toast.makeText(this,
                    getString(R.string.no_ingredients_selected_message), Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = new Intent(this,RecipesListActivity.class);
        RecipesSearcher search = new RecipesSearcher();

        AdvancedSearchFragment advanced = (AdvancedSearchFragment) mAdapter.getFragmentAtPosition(SearchPagerItem.ADVANCED_SETTINGS.ordinal());
        search.kosher = advanced.isKosher();
        search.vegan = advanced.isVegan();
        search.vegeterian = advanced.isVegetarian();
        search.minRating = advanced.getMinRating();
        search.maxPrepTime = advanced.getMaxPrepTimeMinutes();
//        search.maxDifficulty = advanced.getMaxDifficulty(); //TODO - add max difficulty to search
        List<Ingredient> excludedIngs = advanced.getExcludedIngredients();
        search.excluded = new String[excludedIngs.size()];
        int j=0;
        for (Ingredient ing : excludedIngs) search.excluded[j++] = ing.nameOnServer;


        search.ingredients = new String[selectedIngs.size()];
        int i=0;
        for (Ingredient ing : selectedIngs) search.ingredients[i++]=ing.nameOnServer;
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
        if (item.getItemId()==R.id.save_settings) {
            ((AdvancedSearchFragment)mAdapter.getFragmentAtPosition(SearchPagerItem.ADVANCED_SETTINGS.ordinal())).saveAsDefault();
            Toast.makeText(this, getString(R.string.settings_saved_as_defalut_message), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

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

    public class SearchPagerAdapter extends FragmentPagerAdapter {
        HashMap<Integer, Fragment> registeredFragments = new HashMap<>();
        public SearchPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return SearchPagerItem.values()[position].newFragInstance();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getApplicationContext().getString(SearchPagerItem.values()[position].getPageTitle());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getFragmentAtPosition(int pos){return registeredFragments.get(pos);}
    }

    private enum SearchPagerItem
    {
        GENERAL {
            @Override
            public Fragment newFragInstance() {
                return new SearchFragment();
            }

            @Override
            public int getPageTitle() {
                return R.string.general;
            }
        },
        ADVANCED_SETTINGS {
            @Override
            public Fragment newFragInstance() {
                return new AdvancedSearchFragment();
            }

            @Override
            public int getPageTitle() {
                return R.string.advanced_settings;
            }
        };

        public abstract Fragment newFragInstance();
        public abstract int getPageTitle();
    }
}