package com.example.dayoff.model;

public class LeavesTypeListView {
    int img;
    String leaveNAME;
    String leaveBal;
    public LeavesTypeListView()
    {
    }

    public LeavesTypeListView(int img, String leaveNAME, String leaveBal) {
        this.img = img;
        this.leaveNAME = leaveNAME;
        this.leaveBal = leaveBal;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getLeaveNAME() {
        return leaveNAME;
    }

    public void setLeaveNAME(String leaveNAME) {
        this.leaveNAME = leaveNAME;
    }

    public String getLeaveBal() {
        return leaveBal;
    }

    public void setLeaveBal(String leaveBal) {
        this.leaveBal = leaveBal;
    }
}
