package com.example.dayoff.model;

public class HolidayName {
    String holidayName;
    String startDate;
    String endDate;

    public HolidayName()
    {

    }

    public HolidayName(String holidayName, String startDate, String endDate) {
        this.holidayName = holidayName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
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
