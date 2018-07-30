package com.example.jonassimonaitis.bemyguest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private Button buttonSignIn, buttonSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView forgotPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;
    private String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //If user is logged in, MainActivity will start.
        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        forgotPassword = findViewById(R.id.textViewForgotPassword);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        // Making notification bar transparent
        changeStatusBarColor();


    }

    private void checkIfEmailVerified(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // [Catches NullPointerException]
        try{



            if(user.isEmailVerified()){

                final Intent profileActivity = new Intent(getApplicationContext(), ProfileActivity.class);
                final Intent MainmenuAcitivty = new Intent(getApplicationContext(), MainActivity.class);

                userRef.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            startActivity(MainmenuAcitivty);
                            finish();
                        }else{
                            startActivity(profileActivity);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }else{
                Toast.makeText(this, "Your email " + user.getEmail() + " is not verified!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(getIntent());
                finish();

            }

        }catch (NullPointerException e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // [Finishes try-catch]

    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }


    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }



        //IF validation is correct show a progress bar

        progressDialog.setMessage("Login User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){

                            //start activity
                            checkIfEmailVerified();

                        }else{

                            Toast.makeText(LoginActivity.this,"Incorrect Email or Password, try again.", Toast.LENGTH_SHORT).show();
                            editTextPassword.setText("");

                        }
                    }
                });

    }


    @Override
    public void onClick(View view) {

        if(view == buttonSignIn){

            userLogin();

        }else if(view == buttonSignUp){

            startActivity(new Intent(this, RegistrationActivity.class));

        }else if(view == forgotPassword){

            startActivity(new Intent(this, PasswordRecoveryActivity.class));


        }

    }
}
