package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import networking.Login;

/**
 * Created by Nati on 11/05/2016.
 */
public class TastyDrawerLayout {

    public static final String FROM_DRAWER_KEY = "from drawer";
    private final Activity activity;
    private final Login login;
    private Drawer drawer;
    private AccountHeader header;
    private ITEMS[] items;

    public TastyDrawerLayout(Activity activity, Toolbar toolbar, Login login) {
        this.activity = activity;
        this.login = login;
        this.header = new AccountHeaderBuilder()
                .withActivity(activity)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.drawer_header)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                .withTextColor(activity.getResources().getColor(R.color.md_white_1000))
                .withTranslucentStatusBar(true)
                .build();
        this.drawer = new DrawerBuilder()
                .withToolbar(toolbar)
                .withHeaderPadding(false)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(header, false)
                .withSliderBackgroundColorRes(R.color.material_drawer_background)
                .withActivity(activity)
                .withSelectedItem(-1) //disable selected value
//                .withCloseOnClick(true)
                .build();


        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                items[position - 1].handleSelection(TastyDrawerLayout.this);// TODO - check if user is logged on
                drawer.setSelectionAtPosition(-1);
                return false; //must return false so the drawer will close on click
            }
        });
        drawMenu();
    }

    private static ITEMS[] loggedItems = {ITEMS.SEARCH,ITEMS.MY_RECIPES,ITEMS.FAVORITES,ITEMS.ADD_RECIPE,ITEMS.PREFERENCE,ITEMS.LOGOUT};
    private static ITEMS[] loggedOutItems = {ITEMS.SEARCH,ITEMS.FAVORITES,ITEMS.PREFERENCE,ITEMS.LOGIN};


    public void drawMenu() {
        while (!header.getProfiles().isEmpty()) header.removeProfile(0);
        drawer.removeAllItems();
        Log.i("TastyDrawer","drawmenu");
        if (login.isConnected()) {

            Log.i("TastyDrawer","drawmenu:true");
            header.addProfiles(
                    new ProfileDrawerItem()
                            .withName(login.getUserName())
                            .withEmail(login.getEmail())
                            .withIcon(activity.getResources().getDrawable(R.drawable.chef_icon))
            );
            items = loggedItems;
        } else {

            Log.i("TastyDrawer","drawmenu:false");
            items = loggedOutItems;
        }
        for (ITEMS item : items) {
            drawer.addItem(item.getItem());
        }
    }

    public void handleLogin(GoogleSignInResult result) {
        login.handleSignInResult(result);
        drawMenu();
    }
    /*public static Drawer addDrawer(final Activity activity, Toolbar toolbar) {
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

        return result;

    }*/


    private enum ITEMS
    {
        SEARCH {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.search_recepie).withIcon(R.drawable.search_icon);
            }

            @Override
            public void handleSelection(TastyDrawerLayout drawer) {
                if(drawer.activity instanceof SearchActivity) return;
                Intent intent = new Intent(drawer.activity,  SearchActivity.class);
                intent.putExtra(TastyDrawerLayout.FROM_DRAWER_KEY, true);
                drawer.activity.startActivity(intent);
                drawer.activity.finish();
            }
        },
        MY_RECIPES {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.myRecepies).withIcon(R.drawable.chef_icon);
            }
            @Override
            public void handleSelection(TastyDrawerLayout drawer) {
                //TODO - check if my recipes already displayed
                Intent intent = new Intent(drawer.activity,  RecipesListActivity.class);
                intent.putExtra(TastyDrawerLayout.FROM_DRAWER_KEY, true);
                intent.putExtra(RecipesSupplier.SUPPLIER_KEY, new MockSupplier());//TODO - add supplier
                drawer.activity.startActivity(intent);
                drawer.activity.finish();
            }
        },
        FAVORITES {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.favoritRecepies).withIcon(R.drawable.favorites_icon);
            }
            @Override
            public void handleSelection(TastyDrawerLayout drawer) {
                //TODO - check if favorites already displayed
                Intent intent = new Intent(drawer.activity,  RecipesListActivity.class);
                intent.putExtra(TastyDrawerLayout.FROM_DRAWER_KEY, true);
                intent.putExtra(RecipesSupplier.SUPPLIER_KEY, new MockSupplier());//TODO - add supplier
                drawer.activity.startActivity(intent);
                drawer.activity.finish();
            }
        },
        ADD_RECIPE {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.addRecepie).withIcon(R.drawable.add_recepie);
            }
            @Override
            public void handleSelection(TastyDrawerLayout drawer) {
                if(drawer.activity instanceof AddRecepieActivity) return;
                Intent intent = new Intent(drawer.activity,  AddRecepieActivity.class);
                intent.putExtra(TastyDrawerLayout.FROM_DRAWER_KEY, true);
                drawer.activity.startActivity(intent);
                drawer.activity.finish();
            }
        },
        PREFERENCE {
            @Override
            public IDrawerItem getItem() {
                return new PrimaryDrawerItem().withName(R.string.preferences_title).withIcon(R.drawable.settings_icon);
            }
            @Override
            public void handleSelection(TastyDrawerLayout drawer) {
                if(drawer.activity instanceof SettingsActivity) return;
                Intent intent = new Intent(drawer.activity,  SettingsActivity.class);
                intent.putExtra(TastyDrawerLayout.FROM_DRAWER_KEY, true);
                drawer.activity.startActivity(intent);
                drawer.activity.finish();
            }
        },
        LOGIN {
            @Override
            public IDrawerItem getItem() {
                return new SecondaryDrawerItem().withName(R.string.login).withDescription(R.string.login_description)
                        .withIcon(R.drawable.common_google_signin_btn_icon_dark_normal);
            }
            @Override
            public void handleSelection(TastyDrawerLayout drawer) {
                drawer.login.login();
                drawer.drawMenu();
            }
        },
        LOGOUT {
            @Override
            public IDrawerItem getItem() {
                return new SecondaryDrawerItem().withName(R.string.logout)
                        .withIcon(R.drawable.common_google_signin_btn_icon_dark_normal);
            }
            @Override
            public void handleSelection(TastyDrawerLayout drawer) {
//                drawer.login.logout();
//                drawer.drawMenu();
//                if(drawer.activity instanceof SearchActivity) return;
//                Intent intent = new Intent(drawer.activity,  SearchActivity.class);
//                intent.putExtra(TastyDrawerLayout.FROM_DRAWER_KEY, true);
//                drawer.activity.startActivity(intent);
//                drawer.activity.finish();

                AskOption(drawer).show();


            }

            private AlertDialog AskOption(final TastyDrawerLayout drawer)
            {
                AlertDialog myQuittingDialogBox =new AlertDialog.Builder(drawer.activity)
                        //set message, title, and icon
                        .setMessage(R.string.logout_confirmation)
                        .setIcon(android.R.drawable.ic_dialog_info)

                        .setPositiveButton(R.string.logout_confirmation_positive_btn, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                drawer.login.logout();
                                drawer.drawMenu();
                                if(drawer.activity instanceof SearchActivity) return;
                                Intent intent = new Intent(drawer.activity,  SearchActivity.class);
                                intent.putExtra(TastyDrawerLayout.FROM_DRAWER_KEY, true);
                                drawer.activity.startActivity(intent);
                                drawer.activity.finish();



                                dialog.dismiss();
                            }

                        })



                        .setNegativeButton(R.string.logout_confirmation_negative_btn, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();
                return myQuittingDialogBox;

            }

        };

        public abstract IDrawerItem getItem();

        public abstract void handleSelection(TastyDrawerLayout drawer);

    }
}
