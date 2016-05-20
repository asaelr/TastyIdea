package com.example.asaelr.tastyidea;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Nati on 5/20/2016.
 */
public class IngredientsSelectionListFragment extends Fragment {

    private IngredientList ingredientList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients_selection_list, container, false);
        ingredientList = (IngredientList)view.findViewById(R.id.ingredientSelect);
        return view;
    }


    public List<Ingredient> getIngredients() {
        return ingredientList.getList();
    }
}
