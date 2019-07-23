package com.example.dayoff.model;

public class Setting {

    String names;
    int img;

    public Setting(int img,String names) {
        this.names = names;
        this.img=img;
    }

    public int getImg()
    {
        return img;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
