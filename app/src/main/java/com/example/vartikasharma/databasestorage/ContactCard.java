package com.example.vartikasharma.databasestorage;


public class ContactCard {
    private String contactName;
    private String mobileNo;
    private String totalCallDuration;

    public ContactCard() {
    }

    public ContactCard(String contactName, String mobileNo, String totalCallDuration) {
        this.contactName = contactName;
        this.mobileNo = mobileNo;
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

    public String getTotalCallDuration() {
        return totalCallDuration;
    }

    public void setTotalCallDuration(String totalCallDuration) {
        this.totalCallDuration = totalCallDuration;
    }
}
