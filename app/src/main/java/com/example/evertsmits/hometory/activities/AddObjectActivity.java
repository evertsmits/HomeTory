package com.example.evertsmits.hometory.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.data.SQLiteHelper;
import com.example.evertsmits.hometory.models.Object;

/**
 * Created by Evert Smits on 06/10/2017.
 * Activity for adding objects
 */

public class AddObjectActivity extends AppCompatActivity {

    private static final int SELECTED_PICTURE = 1;
    public EditText _title;
    public EditText _amount;
    public EditText _date;
    int _imgWidth = 640;
    int _imgHeight = 1020;
    public EditText _location;
    public ImageView _image;
    String _pageTitle;
    Uri _uri;
    Bitmap _selectedImg;

    SQLiteHelper _SQLiteHelper;

    /**
     * OnCreate of this activity
     *
     * @param savedInstanceState default variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        init();
    }

    /**
     * init this function is used to initiate the declared variables of the page
     */

    public void init() {
        _title = (EditText) findViewById(R.id.object_name_add);
        _amount = (EditText) findViewById(R.id.object_amount_add);
        _date = (EditText) findViewById(R.id.object_purchase_date_add);
        _location = (EditText) findViewById(R.id.object_location_add);
        _image = (ImageView) findViewById(R.id.object_image_add);
        _SQLiteHelper = SQLiteHelper.getInstance(this);

        _image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(AddObjectActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddObjectActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECTED_PICTURE);
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECTED_PICTURE);
                }
            }
        });

        _pageTitle = getIntent().getStringExtra("title");
    }

    /**
     * onRequestPermissionsResult used to catch the users decision on whether he allows us to access media
     *
     * @param requestCode  the code that is used for verifying the case
     * @param permissions  the array that contains the answers which the user could give
     * @param grantResults the array that contains the users decision
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECTED_PICTURE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECTED_PICTURE);
                } else {
                    Toast.makeText(AddObjectActivity.this, "Please allow the app to have access to your gallery", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * onActivityResult method this method catches the photo the user selected from the gallery
     *
     * @param requestCode the code that is used for verifying the case
     * @param resultCode  the result code of the intent that is sent back by the media provider
     * @param data        the chosen picture URI
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                    _uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(_uri, projection, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    _selectedImg = BitmapFactory.decodeFile(filePath);
                    Bitmap resized = Bitmap.createScaledBitmap(_selectedImg, _imgWidth, _imgHeight, true);
                    Drawable draw = new BitmapDrawable(this.getResources(), resized);
                    _image.setImageResource(0);
                    _image.setBackground(draw);
                }
        }
    }

    /**
     * BackToCategoryView function to return to the category page
     *
     * @param view is used because the xml has an onclick
     */

    public void backToCategoryView(View view) {
        Intent intent = new Intent(this, CategoryViewActivity.class);
        intent.putExtra("title", _pageTitle);
        startActivity(intent);
    }

    /**
     * addObject function to retrieve user input and pass it to the SQLiteHelper
     *
     * @param view is used because the xml has an onclick
     */

    public void addObject(View view) {
        String object_title = _title.getText().toString();
        String amount = _amount.getText().toString();
        String date = _date.getText().toString();
        String location = _location.getText().toString();

        if (!TextUtils.isEmpty(object_title) && !TextUtils.isEmpty(amount) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(location) && _selectedImg != null) {
            Object object = new Object(object_title, amount, date, location, _pageTitle, _uri);
            boolean result = _SQLiteHelper.insertDataObject(object);
            if (!result) {
                Toast.makeText(AddObjectActivity.this, "This object already exists", Toast.LENGTH_LONG).show();
            } else {
                Intent data = new Intent();
                data.putExtra("title", _pageTitle);
                setResult(RESULT_OK, data);
                finish();
            }
        } else {
            //Show a message to the user
            Toast.makeText(AddObjectActivity.this, "Please fill in all the fields..", Toast.LENGTH_LONG).show();
        }

    }

}
