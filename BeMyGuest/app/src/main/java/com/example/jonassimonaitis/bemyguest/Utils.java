package com.example.jonassimonaitis.bemyguest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jonassimonaitis on 04/03/2018.
 */

public class Utils {
    public static String getUID(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts").push();
        String[]urlArray = databaseReference.toString().split("/");
        return urlArray[urlArray.length-1];
    }
}
