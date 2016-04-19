package com.example.asaelr.tastyidea;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.support.v4.app.FragmentManager;
import android.widget.RatingBar;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipesListActivityFragment extends Fragment {
    private String[] recipes;

    public RecipesListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        recipes = getResources().getStringArray(R.array.recipes);
        ListView foodList = (ListView) fragmentView.findViewById(R.id.recipes);

        LayoutInflater li = (LayoutInflater)getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = li.inflate( R.layout.row_recipe, container, false);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rate);
        LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), ContextCompat.getColor(getContext(), R.color.colorAccent));  // Full star

        RecipesAdapter adapter = new RecipesAdapter(getActivity(), recipes);
        foodList.setAdapter(adapter);
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipe = view.findViewById(R.id.name).toString();
                //onRecipeSelected(recipe);
                if (getView().findViewById(R.id.fragment_container) != null) {
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipeViewActivityFragment()).addToBackStack(null).commit();
                }
            }
        });
        return fragmentView;
    }
}