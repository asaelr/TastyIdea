package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Created by Nati on 11/05/2016.
 */
public class TastyDrawerLayout {
    public static void addDrawer(final Activity activity, Toolbar toolbar) {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.drawer_header)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                .withTextColor(activity.getResources().getColor(R.color.md_white_1000))
                .withTranslucentStatusBar(true)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mike@gmail.com").withIcon(activity.getResources().getDrawable(R.drawable.chef_icon))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        final Drawer result = new DrawerBuilder()
                .withToolbar(toolbar)
                .withHeaderPadding(false)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult, false)
                .withSliderBackgroundColorRes(R.color.material_drawer_background)
                .withActivity(activity)
                .withSelectedItem(-1) //disable selected value
//                .withCloseOnClick(true)
                .build();
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        for (ITEMS item : ITEMS.values()) {
            result.addItem(item.getItem());
        }
        result.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                ITEMS.values()[position - 1].handleSelection(activity);// TODO - check if user is logged on
                result.setSelectionAtPosition(-1);
                return false; //must return false so the drawer will close on click
            }
        });
        result.addStickyFooterItem(new SecondaryDrawerItem()
                .withName("Login")
                .withDescription("login for full functionality")
                .withIcon(R.drawable.common_google_signin_btn_icon_dark_normal)

        );


    }


    private enum ITEMS
    {
        SERACH {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.search_recepie).withIcon(R.drawable.search_icon);
            }

            @Override
            public void handleSelection(Activity currentActivity) {
                if(currentActivity instanceof SearchActivity) return;
                currentActivity.startActivity(new Intent(currentActivity, SearchActivity.class));
                currentActivity.finish();
            }
        },
        MY_RECIPES {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.myRecepies).withIcon(R.drawable.chef_icon);
            }
            @Override
            public void handleSelection(Activity currentActivity) {
                if(currentActivity instanceof RecipesListActivity) return;
                currentActivity.startActivity(new Intent(currentActivity, RecipesListActivity.class));
                currentActivity.finish();
            }
        },
        FAVORITES {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.favoritRecepies).withIcon(R.drawable.favorites_icon);
            }
            @Override
            public void handleSelection(Activity currentActivity) {
                if(currentActivity instanceof RecipesListActivity) return;
                currentActivity.startActivity(new Intent(currentActivity, RecipesListActivity.class));
                currentActivity.finish();
            }
        },
        ADD_RECIPE {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.addRecepie).withIcon(R.drawable.add_recepie);
            }
            @Override
            public void handleSelection(Activity currentActivity) {
                if(currentActivity instanceof AddRecepieActivity) return;
                currentActivity.startActivity(new Intent(currentActivity, AddRecepieActivity.class));
                currentActivity.finish();
            }
        },
        PREFERENCE {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.preferences_title).withIcon(R.drawable.settings_icon);
            }
            @Override
            public void handleSelection(Activity currentActivity) {
                if(currentActivity instanceof SettingsActivity) return;
                currentActivity.startActivity(new Intent(currentActivity, SettingsActivity.class));
                currentActivity.finish();
            }
        };

        public abstract IDrawerItem getItem();

        public void handleSelection(Activity currentActivity)
        {

        }
    }
}
