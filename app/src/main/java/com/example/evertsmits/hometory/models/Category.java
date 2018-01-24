package com.example.evertsmits.hometory.models;


/**
 * Created by Evert Smits on 06/10/2017.
 * Object model for a category
 */


public class Category {

    private int Category_Id;
    public String title;


    /**
     * constructor for a category
     */
    public Category() {
    }

    /**
     * constructor for a category with a provided title
     *
     * @param title title of the category
     */
    public Category(String title) {
        this.title = title;
    }

    /**
     * gets the category id
     *
     * @return the id of the category
     */

    public int getId() {
        return Category_Id;
    }

    /**
     * sets the category id
     *
     * @param id the id that needs to be set
     */

    public void setId(int id) {
        this.Category_Id = id;
    }

    /**
     * gets the category title
     *
     * @return title of the category
     */

    public String getTitle() {
        return title;
    }

    /**
     * sets the category title
     *
     * @param title the title that needs to be set
     */

    public void setTitle(String title) {
        this.title = title;
    }

}
