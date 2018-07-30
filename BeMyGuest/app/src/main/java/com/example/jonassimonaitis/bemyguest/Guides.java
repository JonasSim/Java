package com.example.jonassimonaitis.bemyguest;

/**
 * Created by jonassimonaitis on 25/03/2018.
 */

public class Guides {

    String photoURL, name, surName, date, phoneNumber;

    public Guides(){

    }

    public Guides(String photoURL, String name, String surName, String date, String phoneNumber) {
        this.photoURL = photoURL;
        this.name = name;
        this.surName = surName;
        this.date = date;
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
