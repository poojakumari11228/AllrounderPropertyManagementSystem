package com.example.hp.firebaseconnection;

public class UserJobInformation {

    public String uname;
    public String uEmail;
    public String uJobTitle;
    public String uDesc;
    public String uContact;
    public String uFees;
    public String uImgUrl;

    public UserJobInformation(){}

    public UserJobInformation(String uname, String uEmail, String ujobTitle, String uDesc, String uContact, String uFees, String uImgUrl) {
        this.uname = uname;
        this.uEmail=uEmail;
        this.uJobTitle = ujobTitle;
        this.uDesc=uDesc;
        this.uContact=uContact;
        this.uFees=uFees;
        this.uImgUrl = uImgUrl;

    }

    public String getUname() {
        return uname;
    }

    public String getuJobTitle() {
        return uJobTitle;
    }

    public String getuImgUrl() {
        return uImgUrl;
    }

    public String getuEmail() {
        return uEmail;
    }

    public String getuDesc() {
        return uDesc;
    }

    public String getuContact() {
        return uContact;
    }

    public String getuFees() {
        return uFees;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setuJobTitle(String uJobTitle) {
        this.uJobTitle = uJobTitle;
    }

    public void setuImgUrl(String uImgUrl) {
        this.uImgUrl = uImgUrl;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public void setuDesc(String uDesc) {
        this.uDesc = uDesc;
    }

    public void setuContact(String uContact) {
        this.uContact = uContact;
    }

    public void setuFees(String uFees) {
        this.uFees = uFees;
    }
}
