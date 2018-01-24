package com.example.evertsmits.hometory.models;


import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Evert Smits on 06/10/2017.
 * Object model for an Object
 */

public class Object implements Serializable {
    public String title;
    public String amount;
    public String purchase_date;
    public String location;
    public String category;
    public Uri uri;

    /**
     * constructor for an object
     */
    public Object() {

    }

    /**
     * constructor for an object with parameters
     *
     * @param title         title for the object
     * @param amount        amount for the object
     * @param purchase_date purchase date for the object
     * @param location      location for the object
     * @param category      category for the object
     * @param uri           uri from the image of the object
     */

    public Object(String title, String amount, String purchase_date, String location, String category, Uri uri) {
        this.title = title;
        this.amount = amount;
        this.purchase_date = purchase_date;
        this.location = location;
        this.category = category;
        this.uri = uri;
    }

    /**
     * gets the object Uri
     *
     * @return the Uri of the object
     */

    public Uri getUri() {
        return uri;
    }

    /**
     * sets the object Uri
     *
     * @param uri uri that needs to be set
     */

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    /**
     * gets the object title
     *
     * @return the title of the object
     */

    public String getTitle() {
        return title;
    }

    /**
     * sets the object title
     *
     * @param title title that needs to be set
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets the object amount
     *
     * @return the amount of the object
     */

    public String getAmount() {
        return amount;
    }

    /**
     * gets the object category
     *
     * @return the category of the object
     */

    public String getCategory() {
        return category;
    }

    /**
     * sets the object amount
     *
     * @param amount amount that needs to be set
     */

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * gets the object purchase date
     *
     * @return the purchase date of the object
     */

    public String getPurchase_date() {
        return purchase_date;
    }

    /**
     * sets the object purchase date
     *
     * @param purchase_date purchase date that nees to be set
     */

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    /**
     * gets the object location
     *
     * @return the location of the object
     */

    public String getLocation() {
        return location;
    }

    /**
     * sets the object location
     *
     * @param location location that needs to be set
     */

    public void setLocation(String location) {
        this.location = location;
    }
}
