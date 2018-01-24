package com.example.evertsmits.hometory.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.evertsmits.hometory.models.Category;
import com.example.evertsmits.hometory.models.House;
import com.example.evertsmits.hometory.models.Object;

/**
 * Created by Evert Smits on 03/10/2017.
 * Database helper in order to perform sql statements and initialize the database
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteHelper _Instance = null;

    private static final String DATABASE_NAME = "HomeTory.SQLite";
    private static final String DATABASE_CATEGORY_TABLE = "Category";
    private static final String DATABASE_HOUSE_TABLE = "House";
    private static final String DATABASE_OBJECT_TABLE = "Object";
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructor for a new SQLiteHelper
     *
     * @param ctx which context is provided?
     */

    private SQLiteHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * getInstance method returns the same instance of the SQLiteHelper if not already created
     *
     * @param ctx which context is provided?
     * @return returns a instance of the SQLiteHelper
     */

    public static SQLiteHelper getInstance(Context ctx) {
        //check if there is a instance of the helper in place
        if (_Instance == null) {
            _Instance = new SQLiteHelper(ctx);
        }
        //else return the already created instance this will give every activity the same helper.
        return _Instance;
    }

    /**
     * getCategory method returns the category from the database using a object's title
     *
     * @param title object title
     * @return return a category
     */

    public Category getCategory(String title) {
        SQLiteDatabase database = openWriteAble();
        String sql = "SELECT * FROM " + DATABASE_CATEGORY_TABLE + " WHERE title = " + "'" + title + "'";
        Cursor cursor = database.rawQuery(sql, null);
        Category category = new Category();

        if (cursor.moveToFirst()) {
            category.setId(Integer.parseInt(cursor.getString(0)));
            category.setTitle(cursor.getString(1));
            cursor.close();
        } else {
            category = null;
        }
        database.close();
        return category;
    }


    public Cursor getHouse() {
        SQLiteDatabase database = openReadable();
        String sql = "SELECT * FROM " + DATABASE_HOUSE_TABLE;
        return database.rawQuery(sql, null);
    }

    /**
     * insertDataCategory inserts a new category into the database
     *
     * @param category the category to insert
     * @return a boolean in order to prevent the user from adding duplicate categories
     */

    public boolean insertDataCategory(Category category) {
        Cursor cursor = getCategories();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getString(1).compareTo(category.title) == 0) {
                return false;
            }
        }
        SQLiteDatabase database = openWriteAble();
        String sql = "INSERT INTO " + DATABASE_CATEGORY_TABLE + " VALUES (NULL, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, category.getTitle());
        statement.executeInsert();
        database.close();
        return true;
    }

    /**
     * removeDataCategory removes the provided category from the database
     *
     * @param category the category to remove
     */

    public void removeDataCategory(Category category) {
        SQLiteDatabase database = openWriteAble();
        String sql = "DELETE FROM " + DATABASE_CATEGORY_TABLE + " WHERE Id = " + category.getId();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.executeUpdateDelete();
        database.close();
    }

    /**
     * updateObject this method updates the database with the new object
     *
     * @param object   the object that needs to replace the old object
     * @param oldTitle title used to find the object that needs replacement
     */

    public void updateObject(Object object, String oldTitle) {
        SQLiteDatabase database = openWriteAble();
        String sql = "UPDATE " + DATABASE_OBJECT_TABLE + " SET title = ?, amount = ?, purchase_date = ?, location = ?, uri = ? WHERE title = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, object.getTitle());
        statement.bindString(2, object.getAmount());
        statement.bindString(3, object.getPurchase_date());
        statement.bindString(4, object.getLocation());
        String uri = object.getUri().toString();
        statement.bindString(5, uri);
        statement.bindString(6, oldTitle);
        statement.executeUpdateDelete();
        database.close();
    }

    /**
     * removeObject this function removes the provided object from the database
     *
     * @param object the object that needs to be removed
     */

    public void removeObject(Object object) {
        SQLiteDatabase database = openWriteAble();
        String sql = "DELETE FROM " + DATABASE_OBJECT_TABLE + " WHERE title = " + "'" + object.title + "'";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.executeUpdateDelete();
        database.close();
    }

    /**
     * insertDataObject this function inserts the provided object into the database
     *
     * @param object the object to insert into the database
     * @return a boolean in order to prevent the user from adding duplicate objects
     */

    public boolean insertDataObject(Object object) {
        Cursor cursor = getObjects(object.category);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getString(1).compareTo(object.title) == 0) {
                return false;
            }
        }
        cursor.close();
        SQLiteDatabase database = openWriteAble();
        String sql = "INSERT INTO " + DATABASE_OBJECT_TABLE + " VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, object.getTitle());
        statement.bindString(2, object.getAmount());
        statement.bindString(3, object.getPurchase_date());
        statement.bindString(4, object.getLocation());
        statement.bindString(5, object.getCategory());
        String uri = object.getUri().toString();
        statement.bindString(6, uri);
//        statement.bindString(5, image);
        statement.executeInsert();
        database.close();
        return true;
    }

    public void insertDataHouse(House house) {
        SQLiteDatabase database = openWriteAble();
        String sql = "INSERT INTO " + DATABASE_HOUSE_TABLE + " VALUES (NULL, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, house.getHouseName());
        statement.bindString(2, house.getBuildYear());
        statement.bindString(3, house.getStreet());
        statement.bindString(4, house.getZipCode());
        statement.executeInsert();
        database.close();
    }

    /**
     * getCategories a function that gets the categories from the database
     *
     * @return returns a cursor that can be used to get the categories in the activity
     */

    public Cursor getCategories() {
        SQLiteDatabase database = openReadable();
        String sql = "SELECT * FROM " + DATABASE_CATEGORY_TABLE;
        return database.rawQuery(sql, null);
    }

    /**
     * getObjects a function that gets the objects from the database where the category is adequate
     *
     * @param title the title of the category
     * @return returns a cursor that can be used to get the objects in the activity
     */

    public Cursor getObjects(String title) {
        SQLiteDatabase database = openReadable();
        String sql = "SELECT * FROM " + DATABASE_OBJECT_TABLE + " WHERE category = " + "'" + title + "'";
        return database.rawQuery(sql, null);

    }

    /**
     * openReadable a function that returns a readable database
     *
     * @return return a readable database
     */

    private SQLiteDatabase openReadable() {
        return getReadableDatabase();
    }

    /**
     * openWritable a function that returns a writable database
     *
     * @return return a writable database
     */

    private SQLiteDatabase openWriteAble() {
        return getWritableDatabase();
    }

    /**
     * onCreate function that initializes the database and its tables if not already exists
     *
     * @param sqLiteDatabase a sqLiteDatabase object
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_CATEGORY_TABLE + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_OBJECT_TABLE + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, amount VARCHAR, purchase_date VARCHAR, location VARCHAR, category VARCHAR, uri VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_HOUSE_TABLE + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, house_name VARCHAR, build_year VARCHAR, street VARCHAR, zip_code VARCHAR)");
    }

    /**
     * onUpgrade upgrades the database on new app version
     *
     * @param db         the specific database object
     * @param oldVersion the old database version
     * @param newVersion the new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_OBJECT_TABLE);
        onCreate(db);
    }
}
