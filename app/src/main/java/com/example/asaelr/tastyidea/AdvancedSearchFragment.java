package com.example.asaelr.tastyidea;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nati on 5/19/2016.
 */
public class AdvancedSearchFragment extends Fragment {

    public static final String MAX_TIME_KEY = "ADVANCED_MAX_TIME";
    public static final String MAX_DIFFICULTY_KEY = "ADVANCED_MAX_DIFFICULTY";
    public static final String EXCLUDED_KEY = "ADVANCED_EXCLUDED";
    public static final String KOSHER_KEY = "ADVANCED_KOSHER";
    public static final String VEGAN_KEY = "ADVANCED_VEGAN";
    public static final String VEGETARIAN_KEY = "ADVANCED_VEGETARIAN";
    private static final String RATING_KEY = "ADVANCED_RATING";
    RatingBar minRating;
    Spinner maxTime;
    Spinner maxDifficulty;
    IngredientList excludedIngredients;
    CheckBox kosher;
    CheckBox vegan;
    CheckBox vegetarian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        setHasOptionsMenu(true);

        minRating = (RatingBar) view.findViewById(R.id.rating_bar);
        LayerDrawable layerDrawable = (LayerDrawable) minRating.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), ContextCompat.getColor(getActivity(), R.color.colorAccent));

        maxTime = (Spinner) view.findViewById(R.id.time_spinner);
        maxDifficulty = (Spinner) view.findViewById(R.id.time_spinner);
        excludedIngredients = (IngredientList) view.findViewById(R.id.ingredientSelect);
        kosher = (CheckBox) view.findViewById(R.id.kosher_checkbox);
        vegan = (CheckBox) view.findViewById(R.id.vegan_checkbox);
        vegetarian = (CheckBox) view.findViewById(R.id.vegetarian_checkbox);

        if(savedInstanceState ==null) {loadDefault();}

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
    }

    public void saveAsDefault()
    {
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(RATING_KEY, minRating.getRating());
        editor.putInt(MAX_TIME_KEY, maxTime.getSelectedItemPosition());
        editor.putInt(MAX_DIFFICULTY_KEY, maxDifficulty.getSelectedItemPosition());

        HashSet<String> excludedNames = new HashSet<>();
        for (Ingredient ing : excludedIngredients.getList()) {
            excludedNames.add(ing.name);
        }

        editor.putStringSet(EXCLUDED_KEY, excludedNames);
        editor.putBoolean(KOSHER_KEY, kosher.isChecked());
        editor.putBoolean(VEGAN_KEY, vegan.isChecked());
        editor.putBoolean(VEGETARIAN_KEY, vegetarian.isChecked());

        editor.apply();
    }

    private void loadDefault()
    {
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        minRating.setRating(sp.getFloat(RATING_KEY, 0));
        maxTime.setSelection(sp.getInt(MAX_TIME_KEY, 0));
        maxDifficulty.setSelection(sp.getInt(MAX_DIFFICULTY_KEY, 0));

        Set<String> excludedNames = sp.getStringSet(EXCLUDED_KEY, Collections.EMPTY_SET);
        for (String ingName : excludedNames) {
            excludedIngredients.add_ingredient(IngCategory.getIngredientByName(ingName));
        }

        kosher.setChecked(sp.getBoolean(KOSHER_KEY, false));
        vegan.setChecked(sp.getBoolean(VEGAN_KEY, false));
        vegetarian.setChecked(sp.getBoolean(VEGETARIAN_KEY, false));
    }

    public boolean isKosher() {
        return kosher.isChecked();
    }

    public boolean isVegan() {
        return vegan.isChecked();
    }

    public boolean isVegetarian() {
        return vegetarian.isChecked();
    }

    public int getMinRating() {
        return Math.round(minRating.getRating());
    }

    public List<Ingredient> getExcludedIngredients() {
        return excludedIngredients.getList();
    }

    public int getMaxPrepTimeMinutes() {
        return Integer.parseInt(getResources().getStringArray(R.array.MaxTimeValues)[maxTime.getSelectedItemPosition()]);
    }
}
