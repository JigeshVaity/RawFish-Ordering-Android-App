package com.example.anaghafishapp.Domain;

import java.io.Serializable;

public class VendorDomain implements Serializable {
    private String pic;
    private Double price;
    private String title;
    private String name;
    private String quantity;

    private String size;
    private int numberInCart;

    public VendorDomain() {
    }

    public VendorDomain(String pic, Double price, String title, String name,String quantity,String size, int numberInCart) {
        this.pic = pic;
        this.price = price;
        this.title = title;
        this.name = name;
        this.quantity = quantity;
        this.size = size;
        this.numberInCart = numberInCart;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
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
}
