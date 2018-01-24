package com.example.evertsmits.hometory.models;

/**
 * Created by Evert Smits on 15/10/2017.
 * House object class describes the blueprint of an house object
 */

public class House {
    private String houseName;
    private String buildYear;
    private String street;
    private String zipCode;

    /**
     * House constructor
     *
     * @param title      title of the house
     * @param buildYear the build year of the house
     * @param street     the street of the house
     * @param zipCode   the zipcode of the house
     */
    public House(String title, String buildYear, String street, String zipCode) {
        this.houseName = title;
        this.buildYear = buildYear;
        this.street = street;
        this.zipCode = zipCode;
    }

    /**
     * getHouse_name method to get the name of the house
     * @return returns the house name
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * getBuild_year method to get the build year of the house
     * @return returns the build year of the house
     */
    public String getBuildYear() {
        return buildYear;
    }

    /**
     * getStreet method to get the street of the house
     * @return returns the street of the house
     */
    public String getStreet() {
        return street;
    }

    /**
     * getZip_code method to get the zip code of the house
     * @return returns the zipcode of the hosue
     */
    public String getZipCode() {
        return zipCode;
    }

}
