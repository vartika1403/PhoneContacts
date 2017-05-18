package com.example.vartikasharma.databasestorage;


import java.util.Date;

public class ContactCard {
    private String contactName;
    private String mobileNo;
    private Date lastCallDayTime;
    private int totalCallDuration;

    public ContactCard() {
    }

    public ContactCard(String contactName, String mobileNo, Date lastCallDayTime, int totalCallDuration) {
        this.contactName = contactName;
        this.mobileNo = mobileNo;
        this.lastCallDayTime = lastCallDayTime;
        this.totalCallDuration = totalCallDuration;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getTotalCallDuration() {
        return totalCallDuration;
    }

    public void setTotalCallDuration(int totalCallDuration) {
        this.totalCallDuration = totalCallDuration;
    }

    public Date getLastCallDayTime() {
        return lastCallDayTime;
    }

    public void setLastCallDayTime(Date lastCallDayTime) {
        this.lastCallDayTime = lastCallDayTime;
    }
}
