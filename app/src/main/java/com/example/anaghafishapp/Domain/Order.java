package com.example.anaghafishapp.Domain;

import java.util.List;

public class Order {
    private String name;
    private String address;
    private String deliveryTime;
    private String totalPrice;
    private List<FoodDomain> products;
    private String loginData;


    public Order(String deliveryTime, String totalPrice, List<FoodDomain> products) {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public Order(String name, String address, String deliveryTime, String totalPrice, List<FoodDomain> products,String loginData) {
        this.name = name;
        this.address = address;
        this.deliveryTime = deliveryTime;
        this.totalPrice = totalPrice;
        this.products = products;
        this.loginData = loginData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<FoodDomain> getProducts() {
        return products;
    }

    public void setProducts(List<FoodDomain> products) {
        this.products = products;
    }
// Getters and setters
    // Make sure to generate getters and setters for all fields

    public String getLoginData() {
        return loginData;
    }

    public void setLoginData(String loginData) {
        this.loginData = loginData;
    }


}

