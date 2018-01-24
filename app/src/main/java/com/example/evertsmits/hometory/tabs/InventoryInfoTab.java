package com.example.evertsmits.hometory.tabs;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.adapters.ExpListAdapter;
import com.example.evertsmits.hometory.data.SQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Evert Smits on 15/10/2017.
 * Tab fragment to use within tabbed layout this one displays inventory info
 */

public class InventoryInfoTab extends Fragment {

    ExpandableListView _inventoryList;
    List<String> _inventoryCategories;
    Map<String, List<String>> _inventoryItems;
    SQLiteHelper _SQLiteHelper;
    ExpandableListAdapter _expandableAdapter;

    /**
     * onCreateView method to inflate the tab xml and combine the needed data to show in the tab
     *
     * @param inflater           inflater used to inflate the xml
     * @param container          the current ViewGroup container
     * @param savedInstanceState the state of the saved instance
     * @return a view that uses the custom tab layout xml combined with the provided data
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_inventoryinfo_tab2, container, false);
        _SQLiteHelper = SQLiteHelper.getInstance(myView.getContext());
        fillList();
        _inventoryList = myView.findViewById(R.id.expandable_list);
        _expandableAdapter = new ExpListAdapter(myView.getContext(), _inventoryCategories, _inventoryItems);
        _inventoryList.setAdapter(_expandableAdapter);
        return myView;
    }

    /**
     * fillList method used to fill the expanded list view with database category objects and their item count
     */
    public void fillList() {
        _inventoryCategories = new ArrayList<>();
        _inventoryItems = new HashMap<>();
        String _amount;

        Cursor data = _SQLiteHelper.getCategories();
        //first we need to clear all our list items in order to prevent duplicate items
        _inventoryCategories.clear();
        if (data != null) {
            if (data.moveToFirst()) {
                do {
                    String title = data.getString(data.getColumnIndex("title"));
                    _inventoryCategories.add(title);
                    List<String> lst = new ArrayList<>();
                    Cursor innerdata = _SQLiteHelper.getObjects(title);
                    if (innerdata != null) {
                        _amount = String.valueOf(innerdata.getCount());
                        lst.add(_amount + " items in category");
                    } else {
                        lst.add("this category is empty");
                    }
                    _inventoryItems.put(_inventoryCategories.get(data.getPosition()), lst);
                } while (data.moveToNext());
            }
            data.close();
        }
    }
}
