package com.example.asaelr.tastyidea;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return li.inflate( R.layout.minimum_rating_pref, parent, false);
    }
}
