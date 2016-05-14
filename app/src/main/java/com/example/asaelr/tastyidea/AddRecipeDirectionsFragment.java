package com.example.asaelr.tastyidea;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Nati on 09/05/2016.
 */
public class AddRecipeDirectionsFragment  extends Fragment {
    private ListView directions;
    private ImageButton addStepBtn;
    private EditText nextStep;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe_directions, container, false);
        directions = (ListView) view.findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>());
        directions.setAdapter(adapter);
        addStepBtn = (ImageButton) view.findViewById(R.id.addStepButton);
        nextStep = (EditText) view.findViewById(R.id.nextStepText);
        directions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                adapter.remove(adapter.getItem(position));
                AskOption(adapter, position).show();
                return false;
            }
        });
        addStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String step = nextStep.getText().toString();
                if(!step.isEmpty())
                {
                    adapter.add(step);
                    nextStep.setText("");
                    directions.smoothScrollToPosition(directions.getMaxScrollAmount());
                }
            }
        });
        return view;
    }


    private AlertDialog AskOption(final ArrayAdapter<String> adapter, final int position)
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
//                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        delete();
                        dialog.dismiss();
                    }

                    private void delete() {
                        adapter.remove(adapter.getItem(position));
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
