package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Created by Nati on 11/05/2016.
 */
public class TastyDrawerLayout {

    public static void addDrawer(Activity activity) {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.mipmap.ic_launcher)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withIcon(activity.getResources().getDrawable(R.drawable.chef_icon))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        new DrawerBuilder().withAccountHeader(headerResult, false).withSliderBackgroundColorRes(R.color.colorAccent).withActivity(activity).build();
    }
}
