package com.example.asaelr.tastyidea;

import android.content.Context;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;

import com.example.asaelr.tastyidea.R;

/**
 * Created by Nati on 14/04/2016.
 */
public class ExcludeIngrediantsPreference extends DialogPreference {


    
    public ExcludeIngrediantsPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(R.layout.select_ingridiants_dialog);
        setPositiveButtonText(R.string.ok);

        setIcon(null);
    }
}
