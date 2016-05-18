package com.example.asaelr.tastyidea.AddRecipe;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.asaelr.tastyidea.Ingredients.IngredientListFragment;
import com.example.asaelr.tastyidea.R;
import com.example.asaelr.tastyidea.Navigation.TastyDrawerLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import networking.Login;

public class AddRecepieActivity extends AppCompatActivity {
    static final int NUM_ITEMS = AddRecipePagerItem.values().length;

    AddRecipePagerAdapter mAdapter;

    private TastyDrawerLayout drawer;
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recepie);

        mAdapter = new AddRecipePagerAdapter(getFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = new TastyDrawerLayout(this,toolbar,new Login(this));
        //TastyDrawerLayout.addDrawer(this, (Toolbar) findViewById(R.id.toolbar));





    }

    public class AddRecipePagerAdapter extends FragmentPagerAdapter {
        public AddRecipePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return AddRecipePagerItem.values()[position].newFragInstance();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getApplicationContext().getString(AddRecipePagerItem.values()[position].getPageTitle());
        }
    }

    private enum AddRecipePagerItem
    {
        ATTRIBUTES {
            @Override
            public Fragment newFragInstance() {
                return new AddRecipeAtrributesFragment();
            }

            @Override
            public int getPageTitle() {
                return R.string.attributes;
            }
        },
        INGREDIENTS {
            @Override
            public Fragment newFragInstance() {
                boolean showEditText = true;
                return IngredientListFragment.newInstance(showEditText);
            }

            @Override
            public int getPageTitle() {
                return R.string.ingredients_title;
            }
        },
        DIRECTIONS {
            @Override
            public Fragment newFragInstance() {
                return new AddRecipeDirectionsFragment();
            }

            @Override
            public int getPageTitle() {
                return R.string.directions_title;
            }
        };

        public abstract Fragment newFragInstance();
        public abstract int getPageTitle();
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

}
