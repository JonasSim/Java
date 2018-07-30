package com.example.jonassimonaitis.bemyguest;

/**
 * Created by jonassimonaitis on 04/03/2018.
 */

public class Post {

    private String imageURL;
    private String postName;
    private String mUid;
    private String longitude;
    private String latitude;


    public Post() {

    }

    public Post(String imageURL, String postName, String mUid, String longitude, String latitude) {
        this.imageURL = imageURL;
        this.postName = postName;
        this.mUid = mUid;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}


