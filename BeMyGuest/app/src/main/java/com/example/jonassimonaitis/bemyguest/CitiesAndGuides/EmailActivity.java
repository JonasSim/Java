package com.example.jonassimonaitis.bemyguest.CitiesAndGuides;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmailActivity extends AppCompatActivity {

    private EditText email_subject, email_compose;
    private TextView guide_id_invisible;
    private Button email_send_button;
    private String guide_id;
    private DatabaseReference mDataRef;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        guide_id = getIntent().getStringExtra("id_for_email");
        email_subject = findViewById(R.id.email_subject_ET);
        email_compose = findViewById(R.id.email_compose_ET);
        email_send_button = findViewById(R.id.send_email_btn);
        guide_id_invisible = findViewById(R.id.aa_id);
        toolbar = findViewById(R.id.email_tool_bar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Back");

        mDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mDataRef.child(guide_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserModelClass umc = dataSnapshot.getValue(UserModelClass.class);
                String user_email = umc.getEmail_address();
                guide_id_invisible.setText(user_email);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        email_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAnEmail();
            }
        });


    }

    private void sendAnEmail() {

         String emailSubject = email_subject.getText().toString().trim();
         String composeEmail = email_compose.getText().toString().trim();
         String emailTo = guide_id_invisible.getText().toString().trim();

         if(!TextUtils.isEmpty(emailSubject) && !TextUtils.isEmpty(composeEmail)){

             Intent email = new Intent(Intent.ACTION_SEND);
             email.putExtra(Intent.EXTRA_EMAIL, new String[] {emailTo});
             email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
             email.putExtra(Intent.EXTRA_TEXT, composeEmail);

             email.setType("message/rfc822"); // get this in description
             startActivity(Intent.createChooser(email, "Choose app to send mail"));

         }else{
             Toast.makeText(getApplicationContext(), "One of the field is empty. Please type something!", Toast.LENGTH_SHORT).show();
         }




    }
}
