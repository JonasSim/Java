package com.example.jonassimonaitis.bemyguest.Settings;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.MainActivity;
import com.example.jonassimonaitis.bemyguest.ProfileActivity;
import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private static final int CHOOSE_IMAGE =  101;
    private Button choosePic, submitProfile;
    private TextView hired_guide;
    private EditText uname, usurname, uphoneNumb, uother, ubDay;

    private CircleImageView roundImageView;

    //Date part
    private DatePickerDialog.OnDateSetListener dateSetListener;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference rootRef, uidRef;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    //Image
    private Uri uriProfileImage;
    private StorageTask mUploadTask;

    String uID;



    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //Firebase database, references etc..
        firebaseAuth = FirebaseAuth.getInstance();
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        uidRef = rootRef.child("Users").child(uID);

        //||------------------------Firebase-End----------------------------||


        uname = rootView.findViewById( R.id.userName );
        usurname = rootView.findViewById( R.id.userSurname );
        uphoneNumb = rootView.findViewById( R.id.userphoneNumber );
        ubDay = rootView.findViewById( R.id.userDoB );
        hired_guide = rootView.findViewById(R.id.huired_Guide_profile);
        roundImageView = rootView.findViewById( R.id.profileUserPicture );
        submitProfile = rootView.findViewById( R.id.editProfileButton );
        submitProfile.setOnClickListener(this);
        choosePic = rootView.findViewById( R.id.changeProfilePic );
        choosePic.setOnClickListener(this);


        mStorageRef = FirebaseStorage.getInstance().getReference("profilepics");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");


        //DatePicker
        ubDay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT ) );
                dialog.show();
            }
        } );

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month = month + 1;

                if(month > 9){
                    final String date = dayOfMonth + "/" + month + "/" + year;
                    ubDay.setText(date);
                }else{
                    final String date = dayOfMonth + "/" + "0"+month + "/" + year;
                    ubDay.setText(date);
                }



            }
        };

        //||-----------------------DatePicker-END----------------------||



        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserModelClass userModelClass = dataSnapshot.getValue(UserModelClass.class);
                String name = userModelClass.getName();
                String surname = userModelClass.getSurName();
                String phoneNumb = userModelClass.getPhoneNumber();
                String dateOfBirth = userModelClass.getDate();
                String hiredGuide = userModelClass.getHiredGuide();
                final String imageURL = userModelClass.getPhotoURL();


                //set Profile Image
                Picasso.with(getContext()).load(imageURL).networkPolicy(NetworkPolicy.OFFLINE).into(roundImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getContext()).load(imageURL).into(roundImageView);
                    }
                });

                //User Personal details

                uname.setText(name);
                usurname.setText(surname);
                uphoneNumb.setText(phoneNumb);
                ubDay.setText(dateOfBirth);
                hired_guide.setText(hiredGuide);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        uidRef.addValueEventListener(eventListener);

        return  rootView;

    }

    //Image picker
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(requestCode == CHOOSE_IMAGE || resultCode == RESULT_OK || data !=null || data.getData() != null){

            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uriProfileImage);
                roundImageView.setImageBitmap(bitmap);

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
        ContentResolver cR = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void updateProfile(){

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        final String usName, usSurname, usPhone, usDoB, imageURL, hiredGuide, user_email, hiredBy;
        final String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        usName = uname.getText().toString().trim();
        usSurname = usurname.getText().toString().trim();
        usPhone = uphoneNumb.getText().toString().trim();
        usDoB = ubDay.getText().toString().trim();
        hiredGuide = hired_guide.getText().toString().trim();
        user_email = user.getEmail();
        hiredBy = "";



        final ProgressDialog progressDialog = new ProgressDialog( getActivity());
        progressDialog.setTitle("Updating...");

        if(TextUtils.isEmpty(usName) || TextUtils.isEmpty(usSurname) || TextUtils.isEmpty(usPhone) ||
                TextUtils.isEmpty(usDoB)){
            Toast.makeText(getActivity(),"Please fill all fields!", Toast.LENGTH_SHORT).show();
        }else{

            if(uriProfileImage != null){

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

                        Toast.makeText( getActivity(), "Upload Successful", Toast.LENGTH_SHORT ).show();

                        UserModelClass umc = new UserModelClass(taskSnapshot.getDownloadUrl().toString(), usName, usSurname, usDoB, usPhone, hiredGuide, user_email, hiredBy);
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference uidRef = rootRef.child("Users").child(uID);
                        uidRef.setValue(umc);
                        progressDialog.dismiss();

                    }
                } ).addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_SHORT ).show();

                    }
                } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress = (100.0) * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage( (int) progress + " % Updating..." );

                    }
                } );

            }

        }







    }

    @Override
    public void onClick(View v) {
        if(v == submitProfile){
            updateProfile();
        }else if(v == choosePic){
            showImageChooser();
        }
    }



}
