package com.example.likonirestaurante.Domain;

import java.io.Serializable;

/**
 * Represents a category in the restaurant carrying details about the items.
 */
public class CategoryDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    // Variables
    private String title;
    private String picture;

    // Constructor

    /**
     * Parameterized constructor to initialize category details.
     *
     * @param title   Title of the category
     * @param picture URL of the category picture
     */
    public CategoryDomain(String title, String picture) {
        this.title = title;
        this.picture = picture;
    }

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
