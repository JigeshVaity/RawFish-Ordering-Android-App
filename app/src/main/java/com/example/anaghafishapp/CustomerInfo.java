package com.example.anaghafishapp;

public class CustomerInfo {
    private String fullName;
    private String phoneNo;
    private String address;
    private String street;
    private String landmark;
    private String pincode;
    private String city;
    private String state;

    public CustomerInfo() {
    }


    public CustomerInfo(String fullName, String phoneNo, String address, String street, String landmark, String pincode, String city, String state) {
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.address = address;
        this.street = street;
        this.landmark = landmark;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
