package com.example.asaelr.tastyidea;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeViewFragment extends Fragment {
    private String[] comments;
    private String[] ingredients_names;
    private String[] ingredients_ammounts;
    private String[] orders;

    public RecipeViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_recipe_view, container, false);

        /*
        ImageView imageView = (ImageView) recipeViewFragment.findViewById(R.id.recipe_pic);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = 170;
        params.height = 170;
        imageView.setLayoutParams(params);

        RatingBar ratingBar = (RatingBar) recipeViewFragment.findViewById(R.id.rate);
        LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), ContextCompat.getColor(getContext(), R.color.colorAccent));  // Full star
*/

        ingredients_names = getResources().getStringArray(R.array.pancakes_ingredients_names);
        ingredients_ammounts = getResources().getStringArray(R.array.pancakes_ingredients_ammounts);
        ListView ingredientsList = (ListView) fragmentView.findViewById(R.id.ingredients);
        IngAdapter ingredientsAdapter = new IngAdapter(getActivity(), ingredients_names,ingredients_ammounts);
        ingredientsList.setAdapter(ingredientsAdapter);

        orders = getResources().getStringArray(R.array.pancakes_preparations);
        ListView ordersList = (ListView) fragmentView.findViewById(R.id.directions);
        OrdersAdapter ordersAdapter = new OrdersAdapter(getActivity(),orders);
        ordersList.setAdapter(ordersAdapter);

        comments = getResources().getStringArray(R.array.pancakes_comments);
        ListView commentsList = (ListView) fragmentView.findViewById(R.id.comments);
        OrdersAdapter commentsAdapter = new OrdersAdapter(getActivity(), comments);
        commentsList.setAdapter(commentsAdapter);

        RatingBar ratingBar = (RatingBar) fragmentView.findViewById(R.id.rate);
        Drawable layerDrawable = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(layerDrawable, ContextCompat.getColor(getContext(), R.color.colorAccent));  // Full star
        ratingBar.setRating(2);

        TextView textView = (TextView) fragmentView.findViewById(R.id.username);
        SpannableString content = new SpannableString("User Recipes");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        return fragmentView;
    }
}
