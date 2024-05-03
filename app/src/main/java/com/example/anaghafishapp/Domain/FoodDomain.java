package com.example.anaghafishapp.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private String title;
    private String pic;
    private String description;
    private Double fee;
    private Double star;
    private String quantity;
    private String size;
    private int numberInCart;

    private String originalfee;
    private int percentage;
    private String category;
    private String name;

    public FoodDomain() {
    }

    public FoodDomain(String title, String pic, Double fee, String description, Double star, String quantity, String size, String originalfee, int percentage, String category) {
        this.title = title;
        this.pic = pic;
        this.fee = fee;
        this.description = description;
        this.star = star;
        this.quantity = quantity;
        this.size = size;
        this.originalfee = originalfee;
        this.percentage = percentage;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOriginalfee() {
        return originalfee;
    }

    public void setOriginalfee(String originalfee) {
        this.originalfee = originalfee;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
