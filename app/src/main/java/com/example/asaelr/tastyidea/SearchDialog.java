package com.example.asaelr.tastyidea;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.ListPreference;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by osher on 5/13/2016.
 */

public class SearchDialog extends DialogPreference {

    public SearchDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPersistent(false);
        setDialogLayoutResource(R.layout.search_dialog);
        setPositiveButtonText(R.string.ok);
        setNegativeButtonText(R.string.cancel);
        setDialogTitle("Advanced Search");
        setIcon(null);
    }
/*
    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle(R.string.advanced_search);
        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);
        super.onPrepareDialogBuilder(builder);
    }
*/
    @Override
    public void onBindDialogView(View view){
        SharedPreferences prefs = getSharedPreferences();
        SwitchPreference vegeterian = (SwitchPreference) findPreferenceInHierarchy("vegeterian_switch");
        Boolean vegeterianStatus = vegeterian.isChecked();
        SwitchPreference vegan = (SwitchPreference) findPreferenceInHierarchy("vegan_switch");
        Boolean veganStatus = vegan.isChecked();
        SwitchPreference kosher = (SwitchPreference) findPreferenceInHierarchy("kosher_switch");
        Boolean kosherStatus = kosher.isChecked();
        //TODO get the excluded ings
        MinRatingPreference min_rating = (MinRatingPreference) findPreferenceInHierarchy("min_rating");
        //TODO get the min_rating
        TimePreference time_pref = (TimePreference) findPreferenceInHierarchy("time_pref_key");
        //TODO get the time
        ListPreference maxDiff = (ListPreference) findPreferenceInHierarchy("prefDifficultyLevel");
        //TODO get the max_diff
        //TODO get selected ings
        //TODO create SearchParams Object and call getAllRecipesMetaData
        super.onBindDialogView(view);
    }

    @Override
    protected View onCreateDialogView() {
        return super.onCreateDialogView();
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
    }
}

