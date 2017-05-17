package com.example.vartikasharma.databasestorage;


public class ContactCard {
    private String contactName;
    private String mobileNo;
    private String email;
    private String lastCallTime;
    private String totalCallDuration;

    public ContactCard() {
    }

    public ContactCard(String contactName, String mobileNo, String email, String lastCallTime, String totalCallDuration) {
        this.contactName = contactName;
        this.mobileNo = mobileNo;
        this.email = email;
        this.lastCallTime = lastCallTime;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastCallTime() {
        return lastCallTime;
    }

    public void setLastCallTime(String lastCallTime) {
        this.lastCallTime = lastCallTime;
    }

    public String getTotalCallDuration() {
        return totalCallDuration;
    }

    public void setTotalCallDuration(String totalCallDuration) {
        this.totalCallDuration = totalCallDuration;
    }


}
