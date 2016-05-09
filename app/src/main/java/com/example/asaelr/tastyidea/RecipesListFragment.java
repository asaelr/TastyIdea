package com.example.asaelr.tastyidea;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipesListFragment extends Fragment {
    private String[] names;
    private String[] times;
    private String[] difficulties;
    private String[] categories;

    public RecipesListFragment() {
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

        RecipesAdapter adapter = new RecipesAdapter(getActivity(),names,times,difficulties,categories); //TODO - should receive recipes list
        foodList.setAdapter(adapter);
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipe = view.findViewById(R.id.name).toString();
                //onRecipeSelected(recipe);
                if (getActivity().findViewById(R.id.fragment_container) != null) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipeViewFragment()).addToBackStack(null).commit();
                }
            }
        });

        return fragmentView;
    }
}