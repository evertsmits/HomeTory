package com.example.evertsmits.hometory.tabs;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.data.SQLiteHelper;

/**
 * Created by Evert Smits on 15/10/2017.
 * Tab fragment to use within tabbed layout this one displays home info
 */

public class HouseInfoTab extends Fragment {

    public TextView _household;
    public TextView _buildingyear;
    public TextView _streetname;
    public TextView _zipcode;
    SQLiteHelper _SQliteHelper;

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
        View myView = inflater.inflate(R.layout.fragment_houseinfo_tab1, container, false);
        _household = myView.findViewById(R.id.household_name);
        _buildingyear = myView.findViewById(R.id.building_year);
        _streetname = myView.findViewById(R.id.street);
        _zipcode = myView.findViewById(R.id.zipcode);
        _SQliteHelper = SQLiteHelper.getInstance(myView.getContext());

        Cursor data = _SQliteHelper.getHouse();
        if (data != null) {
            if (data.moveToFirst()) {
                do {
                    _household.setText(data.getString(data.getColumnIndex("house_name")));
                    _buildingyear.setText(data.getString(data.getColumnIndex("build_year")));
                    _streetname.setText(data.getString(data.getColumnIndex("street")));
                    _zipcode.setText(data.getString(data.getColumnIndex("zip_code")));
                } while (data.moveToNext());
            }
            data.close();
        }

        return myView;
    }

}
