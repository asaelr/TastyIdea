package com.example.asaelr.tastyidea;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeViewFragment extends Fragment {
    private String[] comments;
    private String[] ingredients_names;
    private String[] ingredients_ammounts;
    private String[] orders;

    private static Recipe recipe;


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


    public RecipeViewFragment() {
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


        setView();


        return fragmentView;
    }

    private void setView_old() {

        mainView.setVisibility(recipe == null ? View.GONE : View.VISIBLE);
        if(recipe == null) {return;}

        ingredients_names = getResources().getStringArray(R.array.pancakes_ingredients_names);
        ingredients_ammounts = getResources().getStringArray(R.array.pancakes_ingredients_ammounts);
        orders = getResources().getStringArray(R.array.pancakes_preparations);

        IngAdapter ingredientsAdapter = new IngAdapter(getActivity(), ingredients_names,ingredients_ammounts);
        ingredientsList.setAdapter(ingredientsAdapter);

        OrdersAdapter ordersAdapter = new OrdersAdapter(getActivity(),orders);
        ordersList.setAdapter(ordersAdapter);

//        comments = getResources().getStringArray(R.array.pancakes_comments);
//        ListView commentsList = (ListView) fragmentView.findViewById(R.id.comments);
//        OrdersAdapter commentsAdapter = new OrdersAdapter(getActivity(), comments);
//        commentsList.setAdapter(commentsAdapter);

        Drawable layerDrawable = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(layerDrawable, ContextCompat.getColor(getContext(), R.color.colorAccent));  // Full star
        ratingBar.setRating(2);

        SpannableString content = new SpannableString("User Recipes");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        username.setText(content);
    }

    private void setView() {

        mainView.setVisibility(recipe == null ? View.GONE : View.VISIBLE);
        if(recipe == null) {return;}

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

        image.setImageResource(R.drawable.logo_small); //TODO - add image to Recipe

        category.setText(recipe.getCategory());
        difficulty.setText(getResources().getStringArray(R.array.DifficultyLevel)[recipe.getDifficulty()]);
        time.setText(recipe.getPrepTimeMinutes() + " " + getString(R.string.minutes)) ;
    }

    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
        if(mainView != null) //if mainView is null, onCreateView was not called yet. the recipe view will be updated  by onCreateView
        {
            mainView.setVisibility(View.GONE);
            setView();
            mainView.setVisibility(recipe == null ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_recipe_view, menu);
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
            ammount.setText(" - " + item.second);
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
