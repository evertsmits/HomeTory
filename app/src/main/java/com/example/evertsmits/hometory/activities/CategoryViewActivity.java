package com.example.evertsmits.hometory.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evertsmits.hometory.adapters.ObjectAdapter;
import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.data.SQLiteHelper;
import com.example.evertsmits.hometory.models.Object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Evert Smits on 06/10/2017.
 * Activity for viewing the objects in the category
 */

public class CategoryViewActivity extends AppCompatActivity {

    TextView _titleView;
    EditText _searchText;
    ImageView _closeSearch;
    String _title;
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _adapter;
    private List<Object> _ListItems;
    private int REQUEST_CODE = 1;
    SQLiteHelper _SQLiteHelper;


    /**
     * OnCreate of this activity
     *
     * @param savedInstanceState default variable
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        //init all the variables in a separate function
        init();
        updateUI();
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
     * backToCategoryOverview function to return to the category overview page
     *
     * @param view is used because the xml has an onclick
     */

    public void backToCategoryOverview(View view) {
        Intent intent = new Intent(this, CategoryOverviewActivity.class);
        startActivity(intent);
    }

    /**
     * openAddObject function to open the AddObjectActivity using an intent
     *
     * @param view is used because the xml has an onclick
     */

    public void openAddObject(View view) {
        Intent intent = new Intent(this, AddObjectActivity.class);
        intent.putExtra("title", _title);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * onActivityResult is called when the other activity is done with the intent
     * this function sets the title of the category page
     *
     * @param requestCode code for verifying that this is the right intent
     * @param resultCode  code that the intent was success full usually RESULT_OK
     * @param data        the data that was sent with the intent from the other activity
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == AddObjectActivity.RESULT_OK) {
            _title = data.getStringExtra("title");
        }
    }

    /**
     * init this function is used to initiate the declared variables of the page
     */

    public void init() {
        _titleView = (TextView) findViewById(R.id.title_category_view);
        _searchText = (EditText) findViewById(R.id.searchText);
        _recyclerView = (RecyclerView) findViewById(R.id.RecycView);
        _closeSearch = (ImageView) findViewById(R.id.close_search);
        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _ListItems = new ArrayList<>();
        _SQLiteHelper = SQLiteHelper.getInstance(this);

        //get the intent information
        String name = getIntent().getStringExtra("title");
        //set the title to the local title variable
        _title = name;
        //put the name of the category in the title
        _titleView.setText(name);
        //set on focus change listener for the search bar
        _closeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _searchText.setText("");
                updateUI();
            }
        });
        _searchText.addTextChangedListener(new TextWatcher() {
            /**
             * function to listen on before text changed in the search bar
             * @param charSequence char sequence that the user put in
             * @param i start point of the cursor
             * @param i1 count of characters
             * @param i2 end point of the length behind the cursor
             */

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing
            }

            /**
             * function to listen on text change in the search bar
             * @param charSequence char sequence that the user put in
             * @param i start point of the cursor
             * @param i1 count of characters
             * @param i2 end point of the length behind the cursor
             */

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    updateUI();
                } else {
                    Iterator<Object> it = _ListItems.iterator();
                    while (it.hasNext()) {
                        String title = it.next().getTitle();
                        if (!title.contains(charSequence)) {
                            it.remove();
                        }
                    }
                }
                updateList();
            }

            /**
             * function to listen on after text changed in the search bar
             * @param editable the string of characters that the user put in
             */

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing
            }
        });

    }

    /**
     * setTheObjects this function puts the objects from the database into the _ListItems list
     * using the SQLiteHelper
     */

    public void setTheObjects() {
        Cursor object_cursor = _SQLiteHelper.getObjects(_title);
        //first we need to clear all our list items in order to prevent duplicate items
        _ListItems.clear();
        if (object_cursor != null) {
            if (object_cursor.moveToFirst()) {
                do {
                    String title = object_cursor.getString(object_cursor.getColumnIndex("title"));
                    String amount = object_cursor.getString(object_cursor.getColumnIndex("amount"));
                    String purchase_date = object_cursor.getString(object_cursor.getColumnIndex("purchase_date"));
                    String location = object_cursor.getString(object_cursor.getColumnIndex("location"));
                    String uri = object_cursor.getString(object_cursor.getColumnIndex("uri"));
                    if (uri != null) {
                        Object object = new Object();
                        object.title = title;
                        object.amount = amount;
                        object.purchase_date = purchase_date;
                        object.location = location;
                        object.uri = Uri.parse(uri);
                        _ListItems.add(object);
                    }
                } while (object_cursor.moveToNext());
            }
            object_cursor.close();
        }
    }


    /**
     * updateUI this function is used to initiate an list view adapter when not already available and then
     * notify the adapter that the data set has changed in order to refresh the UI
     */

    public void updateUI() {
        //perform logic for the update of the recycler view
        setTheObjects();

        if (_adapter == null) {
            _adapter = new ObjectAdapter(_ListItems, this);
            _recyclerView.setAdapter(_adapter);
        } else {
            _adapter.notifyDataSetChanged();
        }
    }

    /**
     * updateList this functions notifies the adapter that the data set has changed in order to refresh the UI
     * without setting the objects again
     */
    public void updateList() {
        _adapter.notifyDataSetChanged();
    }


}
