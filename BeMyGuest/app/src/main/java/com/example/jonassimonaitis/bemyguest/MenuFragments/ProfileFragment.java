package com.example.jonassimonaitis.bemyguest.MenuFragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.LoginActivity;
import com.example.jonassimonaitis.bemyguest.MainActivity;
import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.SettingsLayout.SettingsActivity;
import com.example.jonassimonaitis.bemyguest.SettingsLayout.SettingsFragment;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";
    private TextView userProfName, userProfEmail, ratinBarResult;
    private RatingBar ratingBar;
    private Button signOut, profileSettings;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef, uidRef;
    private FirebaseUser current_user;
    private CircleImageView circleImageView;
    private BottomNavigationView mMainNav;
    private String uID, name, email, imageURL;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

//        // |------------------LOOK AT THIS PART LATER---------------------|
//        if(savedInstanceState == null){
//            Toast.makeText(getActivity(),"NOTHING", Toast.LENGTH_SHORT).show();
//
//        }else{
//
//            Toast.makeText(getActivity(),savedInstanceState.getString("name",name=null), Toast.LENGTH_SHORT).show();
//        }
//
//        //|------------------------------- END-PART-----------------------|

        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //Firebase references

        firebaseAuth = FirebaseAuth.getInstance();
        current_user = FirebaseAuth.getInstance().getCurrentUser();
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        uidRef = rootRef.child("Users").child(uID);
        uidRef.keepSynced(true );


        mMainNav = rootView.findViewById(R.id.mainNavigation);
        signOut = rootView.findViewById(R.id.signOutButton);
        profileSettings = rootView.findViewById(R.id.buttonProfileSettings);
        userProfName = rootView.findViewById(R.id.profileUserName);
        userProfEmail = rootView.findViewById(R.id.profileUserEmail);
        circleImageView = rootView.findViewById(R.id.profileUserPicture);
        ratingBar = rootView.findViewById(R.id.ratingBar);
        ratinBarResult = rootView.findViewById(R.id.ratingBarResult);



        ValueEventListener eventListener =  new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               try{

                   UserModelClass userModelClass = dataSnapshot.getValue(UserModelClass.class);
                   name = userModelClass.getName();
                   email = mCurrentUser.getEmail();
                   imageURL = userModelClass.getPhotoURL();

               }catch (NullPointerException e){

                   firebaseAuth.signOut();
                   startActivity(new Intent(getContext(), LoginActivity.class));
                   Toast.makeText(getContext(), "User Profile Error", Toast.LENGTH_SHORT).show();
               }



                //try-catch image
                try{
                    //Picasso.with(getContext()).load(imageURL).into(circleImageView);
                    Picasso.with(getContext()).load(imageURL).placeholder(R.drawable.defaulticon).networkPolicy(NetworkPolicy.OFFLINE).into(circleImageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Picasso.with(getContext()).load(imageURL).placeholder(R.drawable.defaulticon).into(circleImageView);

                        }
                    });
                }catch (IllegalArgumentException e){
                    e.getMessage();
                }


                //User Personal details
                userProfName.setText(name);
                userProfEmail.setText(email);


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        signOut.setOnClickListener(this);
        profileSettings.setOnClickListener(this);



        ratingBar.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                String resultStr = getString(R.string.ratingString);

                ratinBarResult.setText(resultStr + " " + rating);

            }
        } );



        //uidRef.addListenerForSingleValueEvent(eventListener);
        uidRef.addValueEventListener(eventListener);


        return rootView;
    }

    private void movetoActivity(){
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        getActivity().overridePendingTransition(0,0);
        getActivity().finish();
    }

    private void changeFragment(){
        Fragment settingsFragment = new SettingsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.mainframeLayout, settingsFragment).addToBackStack(null).commit();
    }

    private void settingsActivity(){

        startActivity(new Intent(getActivity(), SettingsActivity.class));


    }

    @Override
    public void onClick(View v) {
        if(v == signOut){
            firebaseAuth.signOut();
            movetoActivity();

        }else if(v == profileSettings){
//            changeFragment();
            settingsActivity();

        }
    }




}

