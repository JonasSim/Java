package com.example.jonassimonaitis.bemyguest.MenuFragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.CitiesAndGuides.ChatActivity;
import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatFragment extends Fragment {

    private static final String TAG = "CHAT FRAGMENT";
    private DatabaseReference userRef, guideRef;
    private FirebaseAuth mAuth;
    private CircleImageView guide_image;
    private TextView guide_name, guide_surname, guide_id;
    private Button chat_now_btn;
    private String mChatUser, current_hired_guide;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);

        checkAvailableChats(view);

        return view;
    }

    private void checkAvailableChats(View view) {

        guide_name = view.findViewById(R.id.guide_text_name);
        guide_surname = view.findViewById(R.id.guide_text_surname);
        guide_image = view.findViewById(R.id.guide_image_view);
        chat_now_btn = view.findViewById(R.id.chatnow_button);
        guide_id = view.findViewById(R.id.guide_hidden_id);


        mAuth = FirebaseAuth.getInstance();
        final String current_user = mAuth.getCurrentUser().getUid();


        userRef = FirebaseDatabase.getInstance().getReference().child("Chat");
        guideRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userRef.child(current_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    //Users Ref
                    guideRef.child(current_user).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            UserModelClass guide_aa = dataSnapshot.getValue(UserModelClass.class);
                            final String hired_guide_id = guide_aa.getHiredGuide();
                            final String hired_by_id = guide_aa.getHiredBy();

                            if(!hired_guide_id.equals("")){

                                //Users Ref
                                guideRef.child(hired_guide_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        UserModelClass user = dataSnapshot.getValue(UserModelClass.class);
                                        String guide_nameTV = user.getName();
                                        String guide_surnameTV = user.getSurName();
                                        String guide_imageTV = user.getPhotoURL();

                                        guide_name.setText(guide_nameTV);
                                        guide_surname.setText(guide_surnameTV);
                                        Picasso.with(getContext()).load(guide_imageTV).placeholder(R.drawable.defaulticon).into(guide_image);

                                        chat_now_btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                                chatIntent.putExtra("hiredGuide_ID", hired_guide_id);
                                                chatIntent.putExtra("user_name", current_user);
                                                startActivity(chatIntent);
                                            }
                                        });


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }else{

                                if(!hired_by_id.equals("")){

                                    guideRef.child(hired_by_id).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            UserModelClass user2 = dataSnapshot.getValue(UserModelClass.class);
                                            String guide_nameTV2 = user2.getName();
                                            String guide_surnameTV2 = user2.getSurName();
                                            String guide_imageTV2 = user2.getPhotoURL();

                                            guide_name.setText(guide_nameTV2);
                                            guide_surname.setText(guide_surnameTV2);
                                            Picasso.with(getContext()).load(guide_imageTV2).placeholder(R.drawable.defaulticon).into(guide_image);

                                            chat_now_btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                                    chatIntent.putExtra("hiredGuide_ID", hired_by_id);
                                                    chatIntent.putExtra("user_name", current_user);
                                                    startActivity(chatIntent);
                                                }
                                            });

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }


                            }




                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else{
                    guide_name.setVisibility(View.INVISIBLE);
                    guide_surname.setVisibility(View.INVISIBLE);
                    guide_image.setVisibility(View.INVISIBLE);
                    chat_now_btn.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
