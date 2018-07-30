package com.example.jonassimonaitis.bemyguest;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword, editTextConfPassword;
    private TextView textViewSignup, termsAndCons;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        firebaseAuth = FirebaseAuth.getInstance();

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = findViewById(R.id.buttonRegister);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfPassword = findViewById(R.id.editTextPasswordConfirm);
        textViewSignup = findViewById(R.id.textViewSignin);
        termsAndCons = findViewById(R.id.term_and_condTV);

        buttonRegister.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

        termsAndCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent termAndCond = new Intent(getApplication(), TermAndCondActivity.class);
                startActivity(termAndCond);



            }
        });

        //Make status bar transparent
        changeStatusBarColor();

    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void sendVerificationEmail() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        try{

            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //email sent
                        //after email is sent just logout the user and finish this activity
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(RegistrationActivity.this,"Verification email has been sent to " + user.getEmail(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        finish();

                    }else{
                        Toast.makeText(RegistrationActivity.this,"Failed to send an email verification. Try again!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                }
            });

        }catch (NullPointerException e){
            ////Error
        }

    }


    private void registerUser(){

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cpassword = editTextConfPassword.getText().toString().trim();

        //Checks if the user is trying to sign up with registered email
        if(!TextUtils.isEmpty(email)){

            firebaseAuth.fetchProvidersForEmail(editTextEmail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                            try{



                            boolean checkEmail = !task.getResult().getProviders().isEmpty();

                            if(!checkEmail){

                            }else{
                                Toast.makeText(getApplicationContext(), "Email address already exists, try again!", Toast.LENGTH_SHORT).show();
                            }


                            }catch (RuntimeException e){
                                Toast.makeText(getApplicationContext(), "Email address is badly formatted", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });

        }else{
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
        }
        //Check TextFields content
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length() < 6){
            //If password is length is less then 6 populate toast
            Toast.makeText(this, "Password length has to be at least 6 digits", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(cpassword)){
            //cpassword is empty
            Toast.makeText(this, "Please enter confirmation password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals(cpassword)){
            //let though or else
        }else{
            Toast.makeText(this, "Your password do not match. Try again!", Toast.LENGTH_SHORT).show();
            editTextPassword.setText("");
            editTextConfPassword.setText("");
            return;
        }
        //IF validation is correct show a progress bar
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            //start profile activity
                            //profile activity here
                            progressDialog.dismiss();
                            sendVerificationEmail();

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Registration Unsuccessfully!", Toast.LENGTH_SHORT).show();
                            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                                Toast.makeText(RegistrationActivity.this, "Please use correct email form", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }




    public void onClick(View view){

        if (view == buttonRegister) {

            registerUser();
        }

        if(view == textViewSignup){

            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }

    }


}
