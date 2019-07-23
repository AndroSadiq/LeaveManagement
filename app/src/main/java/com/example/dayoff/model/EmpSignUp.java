package com.example.dayoff.model;

public class EmpSignUp {
    String imageUrl;
   private String name;
   private String username;
   private String phone;
   private String password;
           String status;
           String token;

    public EmpSignUp()
    {
    }

    public EmpSignUp(String imageUrl,String name, String username, String phone,String password,String status) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.status = status;
    }

    public EmpSignUp(String imageUrl, String name, String username, String phone, String password, String status, String token) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.status = status;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
