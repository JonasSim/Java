package com.example.jonassimonaitis.bemyguest.Settings;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.Guides;
import com.example.jonassimonaitis.bemyguest.MainActivity;
import com.example.jonassimonaitis.bemyguest.Post;
import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.SettingsLayout.SettingsActivity;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewGuideFragment2 extends Fragment {
    private static final String TAG = "NewGuideFragment";

    private Spinner spinerCity;
    private DatabaseReference databaseReference, olddatabaseRef, newdatabaseRef;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private Button moveGuide;
    private String uID, city, usersGuides;
    private TextView pickedCity, guideName, guideSurname, guideDate, guidePhoneNr, guidePhotoURL;
    private EditText guideDesc;


    public NewGuideFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_new_guide_fragment2, container, false);

        //References
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
        olddatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        newdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Guides").child(uID);

        spinerCity = view.findViewById(R.id.spinnerCities);
        pickedCity = view.findViewById(R.id.pickedCity);
        guideName = view.findViewById(R.id.pickedName);
        guideSurname = view.findViewById(R.id.pickedSurname);
        guideDesc = view.findViewById(R.id.newGuideDescription);
        guideDate = view.findViewById(R.id.pickedDate);
        guidePhoneNr = view.findViewById(R.id.pickedPhoneNumb);
        guidePhotoURL = view.findViewById(R.id.pickedImageURL);
        moveGuide = view.findViewById(R.id.moveGuide);
        moveGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveGuideData();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                final List<String> area = new ArrayList<String>();

                for(DataSnapshot areaSnapshot: dataSnapshot.getChildren()){
                    String areaName = (String) areaSnapshot.child("postName").getValue();
                    area.add(areaName);

                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, area);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinerCity.setAdapter(areasAdapter);
                spinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getContext(), "Picked: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        city = (String) parent.getItemAtPosition(position);
                        String bb = dataSnapshot.getKey();

                        Log.d(TAG, city +  bb );
                        pickedCity.setText(city);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        return view;
    }

    // Other methods -------------------------------------------------------------------------------------------------------

    private void moveGuideData(){

        //Gets data from old database USers
        olddatabaseRef.child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name, surname, date, phoneNumb, photoURL;

                UserModelClass umc = dataSnapshot.getValue(UserModelClass.class);

                name = umc.getName();
                surname = umc.getSurName();
                date = umc.getDate();
                phoneNumb = umc.getPhoneNumber();
                photoURL = umc.getPhotoURL();
                pickedCity.getText().toString().trim();
                guideName.setText(name);
                guideSurname.setText(surname);
                guideDate.setText(date);
                guidePhoneNr.setText(phoneNumb);
                guidePhotoURL.setText(photoURL);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //Moves data to new database

        newdatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String gName, gSurname, gDate, gPhone, gPhoto, gCity, gDescription, gID;

                gName = guideName.getText().toString().trim();
                gSurname = guideSurname.getText().toString().trim();
                gDate = guideDate.getText().toString().trim();
                gPhone = guidePhoneNr.getText().toString().trim();
                gPhoto = guidePhotoURL.getText().toString().trim();
                gCity = pickedCity.getText().toString().trim();
                gDescription = guideDesc.getText().toString().trim();


                gID = uID;

                if(TextUtils.isEmpty(gDescription)){
                    Toast.makeText(getContext(), "Please describe yourself", Toast.LENGTH_SHORT).show();

                    //DO DO list change length size to 20++
                }else if(gDescription.length() < 10){
                    Toast.makeText(getContext(), "Description is to short! Minimum 10 characters", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, gDescription);
                }else{
//                    Toast.makeText(getContext(), "Guide Added!", Toast.LENGTH_SHORT).show();
                    UserModelClass guides = new UserModelClass(gPhoto, gName, gSurname , gDate, gPhone, gCity, gDescription);
                    newdatabaseRef.setValue(guides);
                    startActivity(new Intent(getContext(), MainActivity.class));
                    Toast.makeText(getContext(), "Now you are Guide!", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
