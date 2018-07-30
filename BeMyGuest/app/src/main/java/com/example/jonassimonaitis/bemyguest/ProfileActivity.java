package com.example.jonassimonaitis.bemyguest;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "Date: ";
    private static final int CHOOSE_IMAGE =  101;
    private FirebaseAuth firebaseAuth;
    private Button userButton;
    private EditText uName, uSurname, phoneNumb;
    private TextView DoB;
    private CircleImageView circleImageView;

    //Date part
    private DatePickerDialog.OnDateSetListener dateSetListener;

    //Image part
    private ImageView imageView;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Uri uriProfileImage;
    private StorageTask mUploadTask;
    private FirebaseAuth mUser_email;


    //Shared Resources
    boolean firstTime;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();

        uName = findViewById(R.id.userName);
        uSurname = findViewById(R.id.userSurname);
        phoneNumb = findViewById(R.id.userphoneNumber);
        DoB = findViewById(R.id.dateView);
        userButton = findViewById(R.id.userSubmitButton);
        circleImageView = findViewById(R.id.profileImageUser);
        userButton.setOnClickListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference("profilepics");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        //Avatar picker
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });


        //DatePicker
        DoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(ProfileActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month + 1;

                if (month > 9) {
                    final String date = dayOfMonth + "/" + month + "/" + year;
                    DoB.setText(date);
                } else {
                    final String date = dayOfMonth + "/" + "0" + month + "/" + year;
                    DoB.setText(date);
                }


            }
        };

        //||-------------DatePicker-END---------------------||

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();



    }

    //Image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(requestCode == CHOOSE_IMAGE || resultCode == RESULT_OK || data != null || data.getData() != null){

            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                circleImageView.setImageBitmap(bitmap);

               // uploadImageToFireBaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    //Image chooser method
    private void showImageChooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image" ), CHOOSE_IMAGE );

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadInfo() {

        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setTitle("Uploading..");



        FirebaseUser user = firebaseAuth.getCurrentUser();

        //Variables

        final String name = uName.getText().toString().trim();
        final String surname = uSurname.getText().toString().trim();
        final String userPhoneNumb = phoneNumb.getText().toString().trim();
        final String date = DoB.getText().toString().trim();
        final String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String hiredGuide = "";
        final String user_email = user.getEmail();
        final String hiredBy = "";


        //Checking if all fields are filled
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) ||
                TextUtils.isEmpty(userPhoneNumb) || TextUtils.isEmpty(date)) {

            Toast.makeText(ProfileActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

        } else {

            if (uriProfileImage != null) {

                StorageReference fileReference = mStorageRef.child(uID + "." + getFileExtension( uriProfileImage ) );
                progressDialog.show();


                mUploadTask = fileReference.putFile( uriProfileImage ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Handler hander = new Handler();
                        hander.postDelayed( new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setProgress( 0 );
                            }
                        }, 500 );

                        Toast.makeText( ProfileActivity.this, "Upload Successful", Toast.LENGTH_SHORT ).show();

                        UserModelClass umc = new UserModelClass(taskSnapshot.getDownloadUrl().toString(), name, surname, date, userPhoneNumb, hiredGuide, user_email,hiredBy);
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference uidRef = rootRef.child("Users").child(uID);
                        uidRef.setValue(umc);
                        progressDialog.dismiss();


                        finish();
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    }

                } ).addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText( ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT ).show();

                    }
                } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                        double progress = (100.0) * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage( (int) progress + " % Uploaded..." );

                    }
                } );

            } else {

                Toast.makeText(ProfileActivity.this, "Please choose your Image", Toast.LENGTH_SHORT).show();
                // profile picture is not selected

            }

        }
    }



    @Override
    public void onClick(View view) {

        if(view == userButton){
            if(mUploadTask != null && mUploadTask.isInProgress()){
                Toast.makeText(ProfileActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            }else{
                uploadInfo();
            }

        }

    }




}

