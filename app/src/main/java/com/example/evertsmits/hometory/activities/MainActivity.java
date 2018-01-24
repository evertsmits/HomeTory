package com.example.evertsmits.hometory.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.data.SQLiteHelper;
import com.example.evertsmits.hometory.models.House;

/**
 * class MainActivity created by Evert Smits
 * this is the landings activity on first use. The user fills in his/her house credentials.
 */

public class MainActivity extends AppCompatActivity {

    public EditText _housename;
    public EditText _buildingyear;
    public EditText _street;
    public EditText _zipcode;
    SQLiteHelper _SQLiteHelper;

    /**
     * OnCreate of this activity
     *
     * @param savedInstanceState default variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * openCategoryOverview this function routes the user to the category overview using an intent
     *
     * @param view is used because the xml has an onclick
     */
    public void openCategoryOverview(View view) {
        String housename = _housename.getText().toString();
        String buildingyear = _buildingyear.getText().toString();
        String street = _street.getText().toString();
        String zipcode = _zipcode.getText().toString();

        if (!TextUtils.isEmpty(housename) && !TextUtils.isEmpty(buildingyear) && !TextUtils.isEmpty(street) && !TextUtils.isEmpty(zipcode)) {
            House house = new House(housename, buildingyear, street, zipcode);
            _SQLiteHelper.insertDataHouse(house);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("first_opened", false);
            editor.apply();
            Intent intent = new Intent(this, CategoryOverviewActivity.class);
            startActivity(intent);
        } else {
            //Show a message to the user
            Toast.makeText(MainActivity.this, "Please fill in all the fields..", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * init this function is used to initiate the declared variables of the page
     */
    public void init(){
        _housename = (EditText) findViewById(R.id.enter_house_name);
        _buildingyear = (EditText) findViewById(R.id.enter_building_year);
        _street = (EditText) findViewById(R.id.enter_street_name);
        _zipcode = (EditText) findViewById(R.id.enter_zip_code);
        _SQLiteHelper = SQLiteHelper.getInstance(this);
    }

}
