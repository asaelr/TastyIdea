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
    private String[] names;
    private String[] times;
    private String[] difficulties;
    private String[] categories;

    public RecipesListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        names = getResources().getStringArray(R.array.recipes_names);
        times = getResources().getStringArray(R.array.recipes_times);
        difficulties = getResources().getStringArray(R.array.recipes_difficulties);
        categories = getResources().getStringArray(R.array.recipes_categories);

        ListView foodList = (ListView) fragmentView.findViewById(R.id.recipes);

        LayoutInflater li = (LayoutInflater)getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = li.inflate( R.layout.row_recipe, container, false);

        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rate);
        LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), ContextCompat.getColor(getContext(), R.color.colorAccent));  // Full star

        RecipesAdapter adapter = new RecipesAdapter(getActivity(),names,times,difficulties,categories);
        foodList.setAdapter(adapter);
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipe = view.findViewById(R.id.name).toString();
                //onRecipeSelected(recipe);
                if (view.findViewById(R.id.fragment_container) != null) {
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipeViewActivityFragment()).addToBackStack(null).commit();
                }
            }
        });
        return fragmentView;
    }
}