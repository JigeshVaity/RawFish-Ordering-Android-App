package com.example.anaghafishapp.login;

// UserVendor.java
public class UserVendor {
    private String email;
    private String additionalInfo;
    private String password; // This should be the hashed password

    public UserVendor() {
        // Default constructor required for calls to DataSnapshot.getValue(UserVendor.class)
    }

    public UserVendor(String email, String additionalInfo, String password) {
        this.email = email;
        this.additionalInfo = additionalInfo;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
// Getters and setters for the fields
}
