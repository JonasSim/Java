package com.example.jonassimonaitis.bemyguest;

/**
 * Created by jonassimonaitis on 23/02/2018.
 */

public class UserModelClass {

    private String photoURL, name, surName, date, phoneNumber, pickedCity, description, hiredGuide, email_address, hiredBy;


    public UserModelClass(){

    }

    //User Model
    public UserModelClass(String photo,String name, String sureName, String date, String number, String hiredGuide, String EMAIL, String hiredBy){

        this.photoURL = photo;
        this.name = name;
        this.surName = sureName;
        this.date = date;
        this.phoneNumber = number;
        this.hiredGuide = hiredGuide;
        this.email_address = EMAIL;
        this.hiredBy = hiredBy;
    }

    //Guide Model
    public UserModelClass(String photo,String name, String sureName, String date, String number, String pickedCity, String description){

        this.photoURL = photo;
        this.name = name;
        this.surName = sureName;
        this.date = date;
        this.phoneNumber = number;
        this.pickedCity = pickedCity;
        this.description = description;

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

    public String getPickedCity() {
        return pickedCity;
    }

    public void setPickedCity(String pickedCity) {
        this.pickedCity = pickedCity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHiredGuide() {
        return hiredGuide;
    }

    public void setHiredGuide(String hiredGuide) {
        this.hiredGuide = hiredGuide;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getHiredBy() {
        return hiredBy;
    }

    public void setHiredBy(String hiredBy) {
        this.hiredBy = hiredBy;
    }
}
