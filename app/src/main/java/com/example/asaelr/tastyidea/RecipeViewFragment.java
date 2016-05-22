package com.example.asaelr.tastyidea;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import networking.Login;
import networking.Networking;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeViewFragment extends Fragment {
    private Recipe recipe;

    private View mainView; //should contain all recipe view elements
    private ListView ingredientsList;
    private ListView ordersList;
    private RatingBar ratingBar;
    private TextView username;
    private ImageView image;
    private TextView category;
    private TextView time;
    private TextView difficulty;
    private TextView name;
    private ProgressBar progressBar;

    LoginDataSupplier login;

    public RecipeViewFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            login = (LoginDataSupplier) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LoginDataSupplier");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        setHasOptionsMenu(true);

        mainView = fragmentView.findViewById(R.id.mainView);
        ingredientsList = (ListView) fragmentView.findViewById(R.id.ingredients);
        ordersList = (ListView) fragmentView.findViewById(R.id.directions);
        ratingBar = (RatingBar) fragmentView.findViewById(R.id.rate);
        username = (TextView) fragmentView.findViewById(R.id.username);
        image = (ImageView) fragmentView.findViewById(R.id.recipe_pic);
        category = (TextView) fragmentView.findViewById(R.id.category);
        time = (TextView) fragmentView.findViewById(R.id.time);
        difficulty = (TextView) fragmentView.findViewById(R.id.difficulty);
        name = (TextView) fragmentView.findViewById(R.id.recipeName);
        progressBar = null;//(ProgressBar) fragmentView.findViewById(R.id.recipe_pic_loading);

        if(savedInstanceState!=null) recipe = (Recipe) savedInstanceState.getSerializable("recipe");

        setView();

        return fragmentView;
    }

    private void setView() {

        if (recipe==null) {
            mainView.setVisibility(View.INVISIBLE);
            return;
        }

        mainView.setVisibility(View.VISIBLE);

        name.setText(recipe.getName());

        RecipeIngAdapter ingredientsAdapter = new RecipeIngAdapter(getActivity(), recipe.getIngredients());
        ingredientsList.setAdapter(ingredientsAdapter);

        RecipesDirectionsAdapter ordersAdapter = new RecipesDirectionsAdapter(getActivity(),recipe.getDirections());
        ordersList.setAdapter(ordersAdapter);

        Drawable layerDrawable = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(layerDrawable, ContextCompat.getColor(getContext(), R.color.colorAccent));  // Full star
        ratingBar.setRating(recipe.getRate());

        SpannableString content = new SpannableString(recipe.getUsername());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        username.setText(content);

        image.setImageResource(R.drawable.logo_small); //TODO - temp image, add image to Recipe

        category.setText(recipe.getCategory());
        difficulty.setText(getResources().getStringArray(R.array.DifficultyLevel)[recipe.getDifficulty()]);
        time.setText(recipe.getPrepTimeMinutes() + " " + getString(R.string.minutes)) ;

        new ImageDownloader(recipe.getId(),image, progressBar);
    }

    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
        if(mainView != null) //if mainView is null, onCreateView was not called yet. the recipe view will be updated  by onCreateView
        {
            setView();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_recipe_view, menu);
//        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) { //TODO - set menu items according to currently connected user
        super.onPrepareOptionsMenu(menu);
//        if(recipe == null){
//            menu.clear();
//            return;
//        }
//        menu.findItem(R.id.edit).setVisible(login.isConnected() && login.getUserName() == username.getText());
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (recipe != null) {
            bundle.putSerializable("recipe", recipe);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;
        switch(item.getItemId())
        {
            case R.id.edit:
                handleEditPressed();
                break;
            case R.id.share:
                handleSharePressed();
                break;
            case R.id.fav:
                handleFavoritesPressed();
                break;
            default:
                handled = false;
                break;
        }
        return handled;
    }

    private void handleFavoritesPressed() { //TODO - implement
        Toast.makeText(getActivity(), "Favorites" + " " + getString(R.string.feature_not_implemented), Toast.LENGTH_SHORT).show();
    }

    private void handleSharePressed() { //TODO - implement
        Toast.makeText(getActivity(), "Share" + " " + getString(R.string.feature_not_implemented), Toast.LENGTH_SHORT).show();
    }

    private void handleEditPressed() { //TODO - implement
        Toast.makeText(getActivity(), "Edit" + " " + getString(R.string.feature_not_implemented), Toast.LENGTH_SHORT).show();
    }

    public class RecipeIngAdapter extends ArrayAdapter<Pair<Ingredient, String>> {
        private final Context context;
        private  List<Pair<Ingredient, String>> ingredients;
        public RecipeIngAdapter(Context context, List<Pair<Ingredient, String>> ingredients) {
            super(context, R.layout.row_ingredients,ingredients);
            this.context = context;
            this.ingredients = ingredients;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_ingredients, parent, false);

            Pair<Ingredient, String> item = ingredients.get(position);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.pic);
            TextView name = (TextView) rowView.findViewById(R.id.name);
            TextView ammount = (TextView)rowView.findViewById(R.id.ammount);

            name.setText(item.first.name);
            ammount.setText(item.second);
            imageView.setImageResource(item.first.picture);
            return rowView;
        }
    }

    public class RecipesDirectionsAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] orders;

        public RecipesDirectionsAdapter(Context context, String[] values) {
            super(context, R.layout.row_orders, values);
            this.context = context;
            this.orders = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_orders, parent, false);
            TextView name = (TextView) rowView.findViewById(R.id.name);
            name.setText(orders[position]);
            return rowView;
        }

    }
}
