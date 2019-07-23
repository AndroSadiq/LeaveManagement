package com.example.dayoff.model;

public class LeaveType {
    String leaveName;
    String leaveBalance;
    public LeaveType()
    {
    }

    public LeaveType(String leaveName, String leaveBalance) {
        this.leaveName = leaveName;
        this.leaveBalance = leaveBalance;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(String leaveBalance) {
        this.leaveBalance = leaveBalance;
    }
}
