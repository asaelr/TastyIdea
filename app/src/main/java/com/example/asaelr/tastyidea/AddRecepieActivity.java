package com.example.asaelr.tastyidea;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class AddRecepieActivity extends AppCompatActivity {
    static final int NUM_ITEMS = AddRecipePagerItem.values().length;

    AddRecipePagerAdapter mAdapter;

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recepie);

        mAdapter = new AddRecipePagerAdapter(getFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);





    }

    public static class AddRecipePagerAdapter extends FragmentPagerAdapter {
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
            return AddRecipePagerItem.values()[position].getPageTitle();
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
            public CharSequence getPageTitle() {
                return "Attributes";
            }
        },
        INGREDIENTS {
            @Override
            public Fragment newFragInstance() {
                return new IngredientListFragment();
            }

            @Override
            public CharSequence getPageTitle() {
                return "Insredients";
            }
        },
        DIRECTIONS {
            @Override
            public Fragment newFragInstance() {
                return new AddRecipeDirectionsFragment();
            }

            @Override
            public CharSequence getPageTitle() {
                return "Directions";
            }
        };

        public abstract Fragment newFragInstance();
        public abstract CharSequence getPageTitle();
    }


}
