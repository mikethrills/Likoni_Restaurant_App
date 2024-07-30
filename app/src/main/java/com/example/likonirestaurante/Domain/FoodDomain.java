package com.example.likonirestaurante.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    //Create Variables
    private String title;
    private String picture;
    private String description;
    private Double fee;
    private Double calories;
    private Double ratings;
    private int time;
    private int NumberInCart;

//Create Constructor


    public FoodDomain(){

    }

    public FoodDomain(String title, String picture, String description, Double fee, Double ratings, int time, Double calories ) {
        this.title = title;
        this.picture = picture;
        this.description = description;
        this.fee = fee;
        this.calories=calories;
        this.ratings = ratings;
        this.time = time;
    }


//Create getters and setters


    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public int getNumberInCart() {
        return NumberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        NumberInCart = numberInCart;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getRatings() {return ratings;    }

    public void setRatings(Double ratings) {this.ratings = ratings;}

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
