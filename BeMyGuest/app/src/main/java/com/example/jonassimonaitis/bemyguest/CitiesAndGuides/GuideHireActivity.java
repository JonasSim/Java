package com.example.jonassimonaitis.bemyguest.CitiesAndGuides;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.MapSupportFragment;
import com.example.jonassimonaitis.bemyguest.MapsActivity;
import com.example.jonassimonaitis.bemyguest.Post;
import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.example.jonassimonaitis.bemyguest.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.example.jonassimonaitis.bemyguest.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuideHireActivity extends AppCompatActivity {

    private static final String TAG = "GuideHireActivity";
    private Toolbar toolbar;
    private DatabaseReference guideRef, userRef;
    private CircleImageView guideImageView;
    private TextView guideName, guideSurname, guideRating, guideDescription;
    private Button buttonHire;
    private RatingBar guideRatingBar;
    private String guide_key = null;
    private String uID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_hire);

        guideImageView = findViewById(R.id.guideProfImageView);
        guideName = findViewById(R.id.guideProfNameTV);
        guideSurname = findViewById(R.id.guideProfSurnameTV);
        guideRating = findViewById(R.id.guideProfRatingTV);
        guideRatingBar = findViewById(R.id.guideProfRatingbar);
        guideDescription = findViewById(R.id.guideProfDescription);
        buttonHire = findViewById(R.id.guideHireBtn);
        buttonHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hireGuide();
            }
        });

        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        guide_key = getIntent().getExtras().getString("guide_id");
        guideRef = FirebaseDatabase.getInstance().getReference().child("Guides");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Log.d(TAG, "AAAASDASD::     " + guide_key);

        // [ TOOLBAR START ] -------------------------
        toolbar = findViewById(R.id.guide_tool_bar);
        setSupportActionBar(toolbar);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Back");

        // [ TOOLBAR END ] ---------------------------

        changeStatusBarColor();
        updateGuideProfile();


    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);
    }

    private void updateGuideProfile(){


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading User Data");
        progressDialog.setMessage("Please wait while we load the user data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        guideRef.child(guide_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String guideProfNameText = (String) dataSnapshot.child("name").getValue();
                String guideProfSurnameText = (String) dataSnapshot.child("surName").getValue();
                String guideProfImage = (String) dataSnapshot.child("photoURL").getValue();
                String guideProfDesc = (String) dataSnapshot.child("description").getValue();

                guideName.setText(guideProfNameText);
                guideSurname.setText(guideProfSurnameText);
                Picasso.with(getApplicationContext()).load(guideProfImage).into(guideImageView);
                guideDescription.setText(guideProfDesc);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void hireGuide() {

        final String guidesKey = guide_key;

        if(!guidesKey.equals(uID) && !guidesKey.equals("")){

            userRef.child(uID).child("hiredGuide").setValue(guidesKey).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        userRef.child(guidesKey).child("hiredBy").setValue(uID).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });

                        Toast.makeText(getApplicationContext(), "Guide Hired!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Guide is not Added", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else{
            Toast.makeText(getApplicationContext(), "You cannot hire yourself!", Toast.LENGTH_SHORT).show();
        }

    }


}
