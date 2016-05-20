package com.example.asaelr.tastyidea;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.util.AttributeSet;
import android.widget.TimePicker;

/**
 * Created by Nati on 5/20/2016.
 */
public class MyTimePicker extends TimePicker {
    public MyTimePicker(Context context) {
        super(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar));
        setIs24HourView(true);
    }
}
