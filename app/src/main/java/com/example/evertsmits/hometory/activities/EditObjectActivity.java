package com.example.evertsmits.hometory.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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
 * Created by Evert Smits on 07/10/2017.
 * Activity for viewing the objects and editing.
 */
public class EditObjectActivity extends AppCompatActivity {

    //title of the category
    String _pageTitle;
    String _oldTitle;
    int _imgWidth = 640;
    int _imgHeight = 1020;
    public static Object _object;
    SQLiteHelper _SQLiteHelper;
    public EditText _title;
    public EditText _amount;
    public EditText _date;
    public EditText _location;
    public ImageView _image;
    private static final int SELECTED_PICTURE = 2;
    Uri _uri;
    Bitmap _selectedImg;

    /**
     * OnCreate of this activity
     *
     * @param savedInstanceState default variable
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_object);

        init();
    }

    /**
     * editObject function to retrieve user input and update the object using the SQLiteHelper with
     * the user input
     *
     * @param view is used because the xml has an onclick
     */

    public void editObject(View view) {
        String object_title = _title.getText().toString();
        String amount = _amount.getText().toString();
        String date = _date.getText().toString();
        String location = _location.getText().toString();

        if (!TextUtils.isEmpty(object_title) && !TextUtils.isEmpty(amount) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(location)) {
            _object.setTitle(String.valueOf(_title.getText()));
            _object.setAmount(String.valueOf(_amount.getText()));
            _object.setLocation(String.valueOf(_location.getText()));
            _object.setPurchase_date(String.valueOf(_date.getText()));
            _object.setUri(_uri);
            _SQLiteHelper.updateObject(_object, _oldTitle);
            finish();
        } else {
            Toast.makeText(EditObjectActivity.this, "Please fill in all the fields..", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * getDrawableFromUri this functions returns a drawable from an uri
     *
     * @param uri the uri provided by the users chosen gallery picture
     * @return a drawable made out of the uri
     */

    public Drawable getDrawableFromUri(Uri uri) {
        Bitmap _selectedImg;
        String filePath;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            do {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                filePath = cursor.getString(columnIndex);
            } while (cursor.moveToNext());
            cursor.close();
            _selectedImg = BitmapFactory.decodeFile(filePath);
            Bitmap resized = Bitmap.createScaledBitmap(_selectedImg, _imgWidth, _imgHeight, true);
            return new BitmapDrawable(this.getResources(), resized);


        } else {
            return null;
        }
    }

    /**
     * init this function is used to initiate the declared variables of the page
     */
    public void init() {
        _pageTitle = getIntent().getStringExtra("title");
        _title = (EditText) findViewById(R.id.object_name_edit);
        _amount = (EditText) findViewById(R.id.object_amount_edit);
        _date = (EditText) findViewById(R.id.object_purchase_date_edit);
        _location = (EditText) findViewById(R.id.object_location_edit);
        _image = (ImageView) findViewById(R.id.object_image_edit);
        _SQLiteHelper = SQLiteHelper.getInstance(this);
        _oldTitle = _object.getTitle();
        _title.setText(_object.getTitle());
        _amount.setText(_object.getAmount());
        _date.setText(_object.getPurchase_date());
        _location.setText(_object.getLocation());
        _uri = _object.getUri();
        Drawable draw = getDrawableFromUri(_uri);
        if (draw != null) {
            _image.setImageDrawable(draw);
        } else {
            Drawable default_img = this.getResources().getDrawable(R.mipmap.ic_launcher, null);
            _image.setImageDrawable(default_img);
        }

        _image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });
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
}
