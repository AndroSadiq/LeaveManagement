package com.example.dayoff.model;

public class SignUp {

    String imageUrl;
    String companyName;
    String username;
    String phone;
    String password;
    String token;

    public SignUp() {
    }

    public SignUp(String imageUrl,String companyName, String username,String phone, String password) {
        this.imageUrl = imageUrl;
        this.companyName = companyName;
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public SignUp(String imageUrl, String companyName, String username, String phone, String password, String token) {
        this.imageUrl = imageUrl;
        this.companyName = companyName;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.token = token;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl;}

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}