package com.example.asaelr.tastyidea;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import networking.RecipeMetadata;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipesListFragment extends Fragment {
    private List<RecipeMetadata> recipesList;
    private boolean loaded;
    private RecipesAdapter adapter;

    public RecipesListFragment() {
    }


    private OnRecipeSelectedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnRecipeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null && savedInstanceState.containsKey("recipes")) {
            Log.i("RLFrag","restoring");
            recipesList = (List<RecipeMetadata>) savedInstanceState.getSerializable("recipes");
            loaded = true;
        } else {
            recipesList = new ArrayList<>();
            loaded = false; //boolean zen. I know...
        }
        adapter = new RecipesAdapter(getActivity(), recipesList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreateView(inflater,container,savedInstanceState);
        final View fragmentView = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        ListView foodList = (ListView) fragmentView.findViewById(R.id.recipes);

//        LayoutInflater li = (LayoutInflater)getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//        View view = li.inflate( R.layout.row_recipe, container, false);

         //TODO - should receive recipes list
        foodList.setAdapter(adapter);
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String recipe = view.findViewById(R.id.name).toString();
                //onRecipeSelected(recipe);


//                if (getActivity().findViewById(R.id.fragment_container) != null) {
//                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipeViewFragment()).addToBackStack(null).commit();
//                }

                mListener.handleRecipeSelected(adapter.getItem(position).id);
            }
        });
        if (loaded) {
            fragmentView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        } else {
            RecipesSupplier supplier = (RecipesSupplier) getActivity().getIntent().getSerializableExtra("supplier");
            Log.i("RLFrag", "networking");
            supplier.supply(new RecipesSupplier.Callback() {
                @Override
                public void onSuccess(RecipeMetadata[] recipes) {
                    adapter.addAll(recipes);
                    fragmentView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    loaded = true;
                }
            });
        }
        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (loaded) {
            bundle.putSerializable("recipes", (ArrayList<RecipeMetadata>) recipesList);
            Log.i("RLFrag", "saveInstance");
        }
    }
}