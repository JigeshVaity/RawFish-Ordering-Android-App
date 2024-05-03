package com.example.anaghafishapp.retrieve;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
public class Product implements Parcelable {
    private String image_url;
    private String phone_number;
    private String price;
    private String product_name;
    private String quantity;
    private String location;
    private String size;
    private String vendor_name;
    private int numberInCart;
    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }

    public Product(String image_url, String phone_number, String price, String product_name, String quantity,String size,String vendor_name,String location) {
        this.image_url = image_url;
        this.phone_number = phone_number;
        this.price = price;
        this.product_name = product_name;
        this.quantity = quantity;
        this.size = size;
        this.vendor_name = vendor_name;
        this.location = location;
    }

    protected Product(Parcel in) {
        image_url = in.readString();
        phone_number = in.readString();
        price = in.readString();
        product_name = in.readString();
        vendor_name = in.readString();
        quantity=in.readString();
        size=in.readString();
        location=in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image_url);
        dest.writeString(phone_number);
        dest.writeString(price);
        dest.writeString(product_name);
        dest.writeString(vendor_name);
        dest.writeString(quantity);
        dest.writeString(size);
    }
}
