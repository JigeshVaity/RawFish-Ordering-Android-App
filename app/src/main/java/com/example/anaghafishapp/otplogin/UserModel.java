package com.example.anaghafishapp.otplogin;

import java.sql.Timestamp;

// UserModel.java

public class UserModel {
    private String phoneNumber;
    private String username;
    private long loginTime;  // New field to store login time



    public UserModel() {
        // Default constructor required for Firebase
    }

    public UserModel(String phoneNumber, String username, long loginTime) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.loginTime = loginTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }
}

