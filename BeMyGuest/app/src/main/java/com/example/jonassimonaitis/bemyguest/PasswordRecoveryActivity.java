package com.example.jonassimonaitis.bemyguest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordRecoveryActivity extends AppCompatActivity implements View.OnClickListener {


    private Button emailRecButton;
    private FirebaseAuth auth;
    private Boolean checkIfSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        auth = FirebaseAuth.getInstance();

        emailRecButton = findViewById(R.id.submitButton);
        emailRecButton.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        // making notification bar transparent
        changeStatusBarColor();


    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    //Send password recovery email address to provided email
    private void sendResetPasswordEmail() {
        final String emailRecovery = ((EditText) findViewById(R.id.recoveryEmailText))
                .getText().toString();

        if(!TextUtils.isEmpty(emailRecovery)){
            auth.sendPasswordResetEmail(emailRecovery)
                    .addOnCompleteListener(PasswordRecoveryActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(PasswordRecoveryActivity.this,
                                        "Reset password code has been emailed to "
                                                + emailRecovery,
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(PasswordRecoveryActivity
                                        .this, LoginActivity.class));
                                finish();

                            } else {

                                Toast.makeText(PasswordRecoveryActivity.this,
                                        "There is a problem with reset password, try later.",
                                        Toast.LENGTH_SHORT).show();
                                checkIfSent = false;
                            }
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "Please enter your email!", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        if(v == emailRecButton){
            sendResetPasswordEmail();
        }
    }
}
