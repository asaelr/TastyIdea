package com.example.asaelr.tastyidea;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.preference.Preference;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.asaelr.tastyidea.R;

/**
 * Created by Nati on 14/04/2016.
 */
public class MinRatingPreference extends Preference {
    public MinRatingPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.minimum_rating_pref);
        setIcon(null);

    }

    @Override
    protected View onCreateView(ViewGroup parent )
    {
        super.onCreateView(parent);
        LayoutInflater li = (LayoutInflater)getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = li.inflate( R.layout.minimum_rating_pref, parent, false);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), ContextCompat.getColor(getContext(), R.color.colorAccent));
        return view;
    }
}
