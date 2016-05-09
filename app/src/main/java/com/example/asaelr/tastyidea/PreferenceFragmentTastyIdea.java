package com.example.asaelr.tastyidea;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by Nati on 18/04/2016.
 */
public class PreferenceFragmentTastyIdea extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private int minRate;
    private String maxDifficulty;
    private boolean vegeterian;
    private boolean vegan;
    private boolean kosher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
        sharedPref.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
        sharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
        .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
            String key)
    {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
        EditTextPreference etp = (EditTextPreference) pref;
        pref.setSummary(etp.getText());
        }
        else if(pref instanceof ListPreference)
        {
        ListPreference lp = (ListPreference) pref;
        pref.setSummary(lp.getEntry());
        }
    }

/*
    SwitchPreference vegeterianSwitch = (SwitchPreference) getView().findViewById(vegeterian_switch);
    vegeterianSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){

            }
        }
    });
*/}
