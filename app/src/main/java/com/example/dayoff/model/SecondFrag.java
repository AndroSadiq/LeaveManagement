package com.example.dayoff.model;

public class SecondFrag {
    int img;
    String reason;
    String startDate;
    String endDate;
public SecondFrag()
{
}
    public SecondFrag(int img, String reason, String startDate, String endDate) {
        this.img = img;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
