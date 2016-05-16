package com.example.asaelr.tastyidea;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;

import java.io.IOException;

import networking.Networking;
import networking.RecipeMetadata;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View fragmentView = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        names = getResources().getStringArray(R.array.recipes_names);
        times = getResources().getStringArray(R.array.recipes_times);
        difficulties = getResources().getStringArray(R.array.recipes_difficulties);
        categories = getResources().getStringArray(R.array.recipes_categories);

        ListView foodList = (ListView) fragmentView.findViewById(R.id.recipes);

//        LayoutInflater li = (LayoutInflater)getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//        View view = li.inflate( R.layout.row_recipe, container, false);

        final RecipesAdapter adapter = new RecipesAdapter(getActivity()); //TODO - should receive recipes list
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

        RecipesSupplier supplier = (RecipesSupplier) getActivity().getIntent().getSerializableExtra("supplier");
        Object obj=getActivity().getIntent().getSerializableExtra("supplier");
        Log.i("RLFrag","supp of type: "+obj.getClass().getName()+" val: "+obj);
        if (supplier!=null) {
            Log.i("RecipesListFragment","custom supplier");
            supplier.supply(adapter);
        } else {

            Log.i("RecipesListFragment","default supplier");
            new AsyncTask<Void, Void, RecipeMetadata[]>() {

                @Override
                protected RecipeMetadata[] doInBackground(Void... params) {
                    try {
                        return Networking.getAllRecipesMetadata();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(RecipeMetadata[] result) {
                    adapter.addAll(result);
                    fragmentView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }
            }.execute();
        }

        return fragmentView;
    }
}