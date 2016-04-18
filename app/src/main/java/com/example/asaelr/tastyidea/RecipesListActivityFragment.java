package com.example.asaelr.tastyidea;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.support.v4.app.FragmentManager;

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