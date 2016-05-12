package com.example.asaelr.tastyidea;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

public class SettingsActivity extends AppCompatPreferenceActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new PreferenceFragmentTastyIdea())
//                .commit();
        setContentView(R.layout.activity_settings);
        TastyDrawerLayout.addDrawer(this, (Toolbar) findViewById(R.id.toolbar));

    }
}