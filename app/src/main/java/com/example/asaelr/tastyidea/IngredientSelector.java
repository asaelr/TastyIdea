package com.example.asaelr.tastyidea;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IngredientSelector.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngredientSelector#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientSelector extends DialogFragment {

    private final IngCategory category;
    private IngredientAdder adder;

    public IngredientSelector(IngredientAdder adder, IngCategory cat) {
        super();
        this.adder = adder;
        this.category = cat;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_ingredient_selector, null);
        builder.setView(view);

        GridView gridView = (GridView) view.findViewById(R.id.ingredients_grid);
        gridView.setAdapter(new IngredientsAdapter(getActivity(), category.ingredients) {

            @Override
            public void clicked(Ingredient ing) {
                dismiss();
                adder.addIngredient(ing);
            }
        });
        return builder.create();
    }
}
