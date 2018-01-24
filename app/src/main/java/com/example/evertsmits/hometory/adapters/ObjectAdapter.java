package com.example.evertsmits.hometory.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.activities.CategoryViewActivity;
import com.example.evertsmits.hometory.activities.EditObjectActivity;
import com.example.evertsmits.hometory.data.SQLiteHelper;
import com.example.evertsmits.hometory.models.Object;

import java.util.List;

/**
 * Created by Evert Smits on 06/10/2017.
 * Object adapter for inflating the information in a custom view
 */

public class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.ViewHolder> {

    private SQLiteHelper _SQLite_helper;

    /**
     * Object adapter constructor
     *
     * @param _listItems listitem list type Object
     * @param _ctx       the specified context
     */
    public ObjectAdapter(List<Object> _listItems, Context _ctx) {
        this._listItems = _listItems;
        this._ctx = _ctx;
    }

    private List<Object> _listItems;
    private Context _ctx;

    /**
     * onCreateViewHolder
     *
     * @param parent   parent viewgroup
     * @param viewType viewType
     * @return returns a new viewHolder with the object adapter as its adapter
     */

    @Override
    public ObjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.objectlist_item, parent, false));
    }

    /**
     * getDrawableFromUri this functions returns a drawable from an uri
     *
     * @param uri    the filepath from the selected image in the gallery
     * @param width  the width that the image should be resized to
     * @param height the height that the image should be resized to
     * @return returns a drawable to draw the image on the image view
     */

    private Drawable getDrawableFromUri(Uri uri, int width, int height) {
        Bitmap _selectedImg;
        String filePath;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = _ctx.getContentResolver().query(uri, projection, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            do {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                filePath = cursor.getString(columnIndex);
            } while (cursor.moveToNext());
            cursor.close();
            _selectedImg = BitmapFactory.decodeFile(filePath);
            Bitmap resized = Bitmap.createScaledBitmap(_selectedImg, width, height, true);
            return new BitmapDrawable(_ctx.getResources(), resized);


        } else {
            return null;
        }
    }

    /**
     * onBindViewHolder a method to combine the object items with the inflated view
     *
     * @param holder   the view holder with the object adapter
     * @param position the position of the object
     */

    @Override
    public void onBindViewHolder(final ObjectAdapter.ViewHolder holder, final int position) {
        final Object object = _listItems.get(position);
        int _imgWidth = 640;
        int _imgHeight = 1020;
        holder._title.setText(object.getTitle());
        holder._amount.setText(object.getAmount());
        holder._date.setText(object.getPurchase_date());
        holder._location.setText(object.getLocation());
        Drawable drawable = getDrawableFromUri(object.getUri(), _imgWidth, _imgHeight);
        if (drawable != null) {
            holder._image.setImageDrawable(drawable);
        } else {
            Drawable default_img = _ctx.getResources().getDrawable(R.mipmap.ic_launcher, null);
            holder._image.setImageDrawable(default_img);
        }

        holder._deleteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick go to delete page using an intent with the clicked object
             * @param view view that is involved
             */
            @Override
            public void onClick(View view) {
                if (_ctx instanceof CategoryViewActivity) {
                    _SQLite_helper.removeObject(object);
                    ((CategoryViewActivity) _ctx).updateUI();
                }
            }
        });

        holder._editButton.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick go to edit page using an intent with the clicked object
             * @param view for view reference
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_ctx, EditObjectActivity.class);
                EditObjectActivity._object = object;
                intent.putExtra("title", object.getCategory());
                _ctx.startActivity(intent);
            }
        });

    }

    /**
     * getItemCount returns the amount of items in the object list
     *
     * @return amount of items in object list
     */
    @Override
    public int getItemCount() {
        return _listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View v;
        private TextView _title;
        private TextView _amount;
        private TextView _date;
        private TextView _location;
        private ImageView _image;
        private ImageButton _deleteButton;
        private ImageButton _editButton;

        /**
         * New viewHolder constructor
         *
         * @param itemView the context view
         */

        private ViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            _title = itemView.findViewById(R.id.object_name);
            _amount = itemView.findViewById(R.id.object_amount);
            _date = itemView.findViewById(R.id.object_purchase_date);
            _location = itemView.findViewById(R.id.object_location);
            _image = itemView.findViewById(R.id.object_image);
            _deleteButton = itemView.findViewById(R.id.deleteButton);
            _editButton = itemView.findViewById(R.id.editButton);
            _SQLite_helper = SQLiteHelper.getInstance(_ctx);

        }
    }


}

