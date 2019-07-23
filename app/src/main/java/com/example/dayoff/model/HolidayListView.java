package com.example.dayoff.model;

public class HolidayListView {
    int img;
    String holidayName1;
    String startDate1;
    String endDate1;
public HolidayListView()
{

}
    public HolidayListView(int img, String holidayName1, String startDate1, String endDate1) {
        this.img = img;
        this.holidayName1 = holidayName1;
        this.startDate1 = startDate1;
        this.endDate1 = endDate1;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getHolidayName1() {
        return holidayName1;
    }

    public void setHolidayName1(String holidayName1) {
        this.holidayName1 = holidayName1;
    }

    public String getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(String startDate1) {
        this.startDate1 = startDate1;
    }

    public String getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(String endDate1) {
        this.endDate1 = endDate1;
    }
}
