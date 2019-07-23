package com.example.dayoff.model;

public class EmpList {
           String imageUrl;
   private String name;
   private String phone;


    public EmpList(String imageUrl,String name, String phone) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.phone = phone;
    }
    public String getName() { return name; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

}
