package com.gogreenyellow.simplemenudemo;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * Created by Paulina Glab on 04.02.2018.
 */

public class SimpleMenu extends PopupWindow {

    private Context context;
    private int selectionIndex;
    private ListView listView;


    public SimpleMenu(final Context context, final String[] options, int selectionIndex) {
        super(context, null, 0, R.style.Widget_AppCompat_Light_PopupMenu);

        this.context = context;
        this.selectionIndex = selectionIndex;

        ArrayAdapter adapter = new ArrayAdapter<>(
                context,
                R.layout.simple_menu_item_checkable,
                options);

        listView = new ListView(context);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(selectionIndex, true);
        int listPadding = context.getResources()
                .getDimensionPixelSize(R.dimen.simple_menu_padding);
        listView.setPadding(0, listPadding, 0, listPadding);
        listView.setDividerHeight(0);

        setWidth(context.getResources()
                .getDimensionPixelSize(R.dimen.simple_menu_dropdown_width));

        setContentView(listView);
        setFocusable(true);
    }


    public void show(View parentView, Rect anchorBounds) {
        int listPadding = context.getResources()
                .getDimensionPixelSize(R.dimen.simple_menu_padding);
        int listItemHeight = context.getResources()
                .getDimensionPixelSize(R.dimen.simple_menu_item_height);

        // Set start position (for selectionIndex = 0)
        int posY = anchorBounds.top - listPadding +
                (anchorBounds.height() - listItemHeight) / 2;

        // Move the drop-down list up depending on selected item's index
        posY -= listItemHeight * selectionIndex;

        showAtLocation(parentView, Gravity.TOP | Gravity.START, anchorBounds.left, posY);
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }

}
