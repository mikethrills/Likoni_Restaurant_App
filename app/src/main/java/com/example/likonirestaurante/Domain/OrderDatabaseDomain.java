package com.example.likonirestaurante.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDatabaseDomain extends FoodDomain implements Serializable {
    private String name;
    private String phoneNumber;
    private String orderId;
    private String mpesaId;
    private String date;
    private String totalAmount;
    String TimeofOrder;
    String paymentTime;
    String processingTime;
    String pickUpTime;
    String cookingTime;

    private String status;
    private ArrayList<FoodDomain> cartItems;

    public OrderDatabaseDomain() {
    }

    public OrderDatabaseDomain(String name, String phoneNumber, String orderId, String mpesaId, String date, String totalAmount, String timeofOrder, String paymentTime, String processingTime, String pickUpTime, String cookingTime, String status, ArrayList<FoodDomain> cartItems) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.orderId = orderId;
        this.mpesaId = mpesaId;
        this.date = date;
        this.totalAmount = totalAmount;
        TimeofOrder = timeofOrder;
        this.paymentTime = paymentTime;
        this.processingTime = processingTime;
        this.pickUpTime = pickUpTime;
        this.status = status;
        this.cartItems = cartItems;
        this.cookingTime = cookingTime;
    }

    public OrderDatabaseDomain(String orderId, ArrayList<FoodDomain> cartItems) {
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getTimeofOrder() {
        return TimeofOrder;
    }

    public void setTimeofOrder(String timeofOrder) {
        TimeofOrder = timeofOrder;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMpesaId() {
        return mpesaId;
    }

    public void setMpesaId(String mpesaId) {
        this.mpesaId = mpesaId;
    }

    public ArrayList<FoodDomain> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<FoodDomain> cartItems) {
        this.cartItems = cartItems;
    }
}
