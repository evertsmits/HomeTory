package com.example.evertsmits.hometory.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.data.SQLiteHelper;
import com.example.evertsmits.hometory.models.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evert Smits on 06/10/2017.
 * Activity for viewing the categories
 */

public class CategoryOverviewActivity extends AppCompatActivity {

    ListView _categories;
    List<String> _categoryNames;
    ArrayAdapter<String> _categoryAdapter;
    SQLiteHelper _SQLiteHelper;

    /**
     * OnCreate of this activity
     *
     * @param savedInstanceState default variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview);
        //init all variables and listeners
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean firstOpened = preferences.getBoolean("first_opened", true);

        if (firstOpened) {

            showWelcomeScreen();
            finish();
        }
        init();
        updateUI();

    }

    /**
     * showWelcomeScreen routes the user to the welcome screen of the app
     */
    public void showWelcomeScreen() {
        Intent intent = new Intent(CategoryOverviewActivity.this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * OnResume of this activity
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * init this function is used to initiate the declared variables of the page
     */
    public void init() {
        _categories = (ListView) findViewById(R.id.Categories);
        _categoryNames = new ArrayList<>();
        _SQLiteHelper = SQLiteHelper.getInstance(this);

        //setting the long click listener
        _categories.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Getting item text to be able to show it in a toast.
                String itemToDelete = (String) adapterView.getItemAtPosition(i);
                Category category = _SQLiteHelper.getCategory(itemToDelete);
                _SQLiteHelper.removeDataCategory(category);
                updateUI();
                return true;
            }
        });

        //setting the click listener
        _categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemToAdd = (String) adapterView.getItemAtPosition(i);
                openCategory(itemToAdd);
            }
        });
    }

    /**
     * setTheCategories this function puts the categories from the database into the _categoryNames list
     * using the SQLiteHelper
     */
    public void setTheCategories() {
        Cursor data = _SQLiteHelper.getCategories();
        //first we need to clear all our list items in order to prevent duplicate items
        _categoryNames.clear();
        if (data != null) {
            if (data.moveToFirst()) {
                do {
                    String title = data.getString(data.getColumnIndex("title"));
                    _categoryNames.add(title);
                } while (data.moveToNext());
            }
        }
        data.close();
    }

    /**
     * openAddCategory this function opens the AddCategoryActivity using an intent
     *
     * @param view is used because the xml has an onclick
     */

    public void openAddCategory(View view) {
        Intent intent = new Intent(this, AddCategoryActivity.class);
        startActivity(intent);
    }

    /**
     * goToHouseInformation this routes the user to the tabbed view about the house/inventory
     *
     * @param view is used because the xml has an onclick
     */
    public void goToHouseInformation(View view) {
        Intent intent = new Intent(this, HomeInfoActivity.class);
        startActivity(intent);
    }

    /**
     * openCategory this function opens the CategoryViewActivity using an intent
     *
     * @param item is used because the xml has an onclick
     */

    public void openCategory(String item) {
        Intent intent = new Intent(this, CategoryViewActivity.class);
        intent.putExtra("title", item);
        startActivity(intent);
    }

    /**
     * updateUI this function is used to initiate an list view adapter when not already available and then
     * notify the adapter that the data set has changed in order to refresh the UI
     */

    private void updateUI() {
        setTheCategories();
        // If the list adapter is null, a new one will be instantiated and set on our list view.
        if (_categoryAdapter == null) {
            // We can use ‘this’ for the context argument because an Activity is a subclass of the Context class.
            // The ‘android.R.layout.simple_list_item_1’ argument refers to the simple_list_item_1 layout of the Android layout directory.
            _categoryAdapter = new ArrayAdapter<>(this, R.layout.categorieslist_item, R.id.name, _categoryNames);
            _categories.setAdapter(_categoryAdapter);
        } else {
            // When the adapter is not null, it has to know what to do when our data set changes, when a change happens we need to call this method on the adapter so that it will update internally.
            _categoryAdapter.notifyDataSetChanged();
        }

    }

}
