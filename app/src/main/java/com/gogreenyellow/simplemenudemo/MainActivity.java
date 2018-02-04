package com.gogreenyellow.simplemenudemo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewGroup weightSettingLayout;
    TextView weightSelectedTextView;
    String[] weightOptions;
    int weightSelection;

    ViewGroup optionSettingLayout;
    TextView optionSelectedTextView;
    String[] availableOptions;
    int currentSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Weight unit - whole option's initialization.
        weightSettingLayout = findViewById(R.id.unit_weight_layout);
        weightSelectedTextView = findViewById(R.id.unit_weight_selected_view);
        weightOptions = getResources().getStringArray(R.array.weight_unit_array);
        weightSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSimpleMenu(weightSettingLayout, weightSelectedTextView, weightOptions, weightSelection);
            }
        });

        weightSelection = 0; // Load saved state here e.g. from SharedPreferences.
        weightSelectedTextView.setText(weightOptions[weightSelection]);


        // Any option's initialization.
        optionSettingLayout = findViewById(R.id.option_layout);
        optionSelectedTextView = findViewById(R.id.option_selected_view);
        availableOptions = getResources().getStringArray(R.array.available_options_array);
        optionSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSimpleMenu(optionSettingLayout, optionSelectedTextView, availableOptions, currentSelection);
            }
        });

        currentSelection = 1; // Load saved state here e.g. from SharedPreferences.
        optionSelectedTextView.setText(availableOptions[currentSelection]);
    }

    public void openSimpleMenu(View wrapperLayout, final TextView currentOptionView, final String[] options, int selectionIndex) {
        int[] loc = new int[2];
        wrapperLayout.getLocationInWindow(loc);
        Rect anchorBounds = new Rect(
                loc[0] + wrapperLayout.getPaddingLeft(),
                loc[1],
                loc[0] + wrapperLayout.getWidth() - wrapperLayout.getPaddingLeft(),
                loc[1] + wrapperLayout.getHeight());

        final SimpleMenu simpleMenu = new SimpleMenu(this, options, selectionIndex);

        simpleMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                saveNewSelection(currentOptionView, i);
                currentOptionView.setText(options[i]);
                simpleMenu.dismiss();
            }
        });

        simpleMenu.show(findViewById(android.R.id.content), anchorBounds);
    }

    public void saveNewSelection(TextView currentOptionView, int i) {
        switch (currentOptionView.getId()) {
            case R.id.unit_weight_selected_view:
                weightSelectedTextView.setText(weightOptions[i]);
                weightSelection = i; // Save the state here e.g. to SharedPreferences.
                break;

            case R.id.option_selected_view:
                optionSelectedTextView.setText(availableOptions[i]);
                currentSelection = i; // Save the state here e.g. to SharedPreferences.
        }
    }
}
