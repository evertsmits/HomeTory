package com.example.evertsmits.hometory.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.data.SQLiteHelper;
import com.example.evertsmits.hometory.models.Category;

/**
 * Created by Evert Smits on 06/10/2017.
 * Activity for creating new categories
 */

public class AddCategoryActivity extends AppCompatActivity {

    private EditText _CategoryName;
    SQLiteHelper _SQLiteHelper;

    /**
     * OnCreate of this activity
     *
     * @param savedInstanceState default variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        init();
    }

    /**
     * BackToCategoryOverview function to return to the category overview page
     *
     * @param view is used because the xml has an onclick
     */
    public void backToCategoryOverview(View view) {
        Intent intent = new Intent(this, CategoryOverviewActivity.class);
        startActivity(intent);
    }

    /**
     * addCategory function to add a category to the SQLiteHelper
     *
     * @param view is used because the xml has an onclick
     */
    public void addCategory(View view) {
        if (!TextUtils.isEmpty(_CategoryName.getText())) {
            String title = _CategoryName.getText().toString();
            Category category = new Category(title);

            boolean result = _SQLiteHelper.insertDataCategory(category);
            if (!result) {
                Toast.makeText(AddCategoryActivity.this, "This category already exists", Toast.LENGTH_LONG).show();
            } else {
                finish();
            }
        } else {
            //Show a message to the user
            Toast.makeText(AddCategoryActivity.this, "Please enter a title in the name field", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * init this function is used to initiate the declared variables of the page
     */
    public void init() {
        _CategoryName = (EditText) findViewById(R.id.edit_category_text);
        _SQLiteHelper = SQLiteHelper.getInstance(this);
    }


}
