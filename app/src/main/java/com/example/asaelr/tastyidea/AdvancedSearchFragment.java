package com.example.asaelr.tastyidea;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TimePicker;

/**
 * Created by Nati on 5/19/2016.
 */
public class AdvancedSearchFragment extends Fragment {
    MyTimePicker timePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        LinearLayout timeLayout = (LinearLayout) view.findViewById(R.id.timeLayout);
        timePicker = new MyTimePicker(getActivity());
        timeLayout.addView(timePicker);

        return view;
    }
}
