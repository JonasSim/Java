package com.example.jonassimonaitis.bemyguest.MenuFragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.CitiesAndGuides.ChatActivity;
import com.example.jonassimonaitis.bemyguest.CitiesAndGuides.EmailActivity;
import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {

    private static final String TAG = "GuideFragment";
    private RecyclerView guideListView;
    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private DatabaseReference guideRef, usersRef, chatRef, messagesRef;
    private FirebaseAuth mAuth;
    private TextView guideID, hGuideName, hGuideSurname, hGuideDesc, hGuideRatingTV, hGuideEmpty, hiredBanner;
    private RatingBar hGuideRatingBar;
    private CircleImageView hGuidePicture;
    private ImageView hired_guide_bg;
    private String uID, hiredGuides;
    private Button connectViaEmail, connectViaChat, finishWithGuide;


    public GuideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide, container, false);


        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        guideID = view.findViewById(R.id.guideID);
        hGuideName = view.findViewById(R.id.hiredGuideName);
        hGuideSurname = view.findViewById(R.id.hiredGuideSurname);
        hGuidePicture = view.findViewById(R.id.hiredGuidePictureURL);
        hGuideDesc = view.findViewById(R.id.hiredGuideDescription);
        hGuideRatingBar = view.findViewById(R.id.hGuideRating);
        hGuideEmpty = view.findViewById(R.id.hiredGuidesEmpty);
        hired_guide_bg = view.findViewById(R.id.hired_guide_background);
        connectViaEmail = view.findViewById(R.id.hiredGuideEmailBtn);
        hiredBanner = view.findViewById(R.id.textView10);
        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        connectViaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectViaEmail();
            }
        });
        connectViaChat = view.findViewById(R.id.hiredGuideChatBtn);
        connectViaChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectViaChat();
            }
        });
        finishWithGuide = view.findViewById(R.id.hiredGuideFinishBtn);
        finishWithGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithGuide();
            }
        });
        guideRef = FirebaseDatabase.getInstance().getReference().child("Guides");
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        chatRef = FirebaseDatabase.getInstance().getReference().child("Chat");
        messagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        updateGuideInfo(view);

        return view;
    }

    //Will terminate with a guide!
    private void finishWithGuide() {

        mAuth = FirebaseAuth.getInstance();
        String curret_user_id = mAuth.getCurrentUser().getUid();
        String current_guide_id = guideID.getText().toString().trim();



        ////------------------REMOVING CHAT NOTE FROM BOTH USER AND GUIDE ----------------

        chatRef.child(curret_user_id).removeValue();
        chatRef.child(current_guide_id).removeValue();

        messagesRef.child(curret_user_id).removeValue();
        messagesRef.child(current_guide_id).removeValue();

        ////------------------REMOVING CHAT NOTE FROM BOTH USER AND GUIDE END----------------

        usersRef.child(curret_user_id).child("hiredGuide").setValue("");
        usersRef.child(current_guide_id).child("hiredBy").setValue("");

        Toast.makeText(getContext(), "Guide is removed!", Toast.LENGTH_SHORT).show();
        setFragment(homeFragment);

    }

    private void connectViaChat() {


         Intent chatIntent = new Intent(getContext(), ChatActivity.class);
         chatIntent.putExtra("hiredGuide_ID", hiredGuides);
         chatIntent.putExtra("user_name", hGuideName.getText().toString());
         startActivity(chatIntent);

    }

    private void connectViaEmail() {

        String guide_id_for_email = guideID.getText().toString().trim();

        Intent emailActivity = new Intent(getContext(), EmailActivity.class);
        emailActivity.putExtra("id_for_email", guide_id_for_email);
        startActivity(emailActivity);

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframeLayout, fragment, "Fragment" );
        fragmentTransaction.commit();


    }


    private void updateGuideInfo(final View view) {

        usersRef.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    UserModelClass umc = dataSnapshot.getValue(UserModelClass.class);
                    hiredGuides = umc.getHiredGuide();
                    guideID.setText(hiredGuides);

                }catch (NullPointerException e){

                }

                if(!hiredGuides.equals("")){


                guideRef.child(hiredGuides).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try{

                                UserModelClass guides = dataSnapshot.getValue(UserModelClass.class);
                                hGuideName.setText(guides.getName());
                                hGuideSurname.setText(guides.getSurName());
                                hGuideDesc.setText(guides.getDescription());
                                Picasso.with(getContext()).load(guides.getPhotoURL()).placeholder(R.drawable.defaulticon).into(hGuidePicture);
                                hGuideEmpty.setVisibility(View.GONE);

                        }catch (NullPointerException e){

                            hGuideName.setVisibility(View.INVISIBLE);
                            hGuideSurname.setVisibility(View.INVISIBLE);
                            hGuideDesc.setVisibility(View.INVISIBLE);
                            hGuidePicture.setVisibility(View.INVISIBLE);
                            hGuideRatingBar.setVisibility(View.INVISIBLE);
                            connectViaEmail.setVisibility(View.INVISIBLE);
                            connectViaChat.setVisibility(View.INVISIBLE);
                            finishWithGuide.setVisibility(View.INVISIBLE);
                            hired_guide_bg.setVisibility(View.INVISIBLE);
                            hiredBanner.setVisibility(View.INVISIBLE);
                            hGuideEmpty.setVisibility(View.VISIBLE);


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                }else{
                    hGuideName.setVisibility(View.INVISIBLE);
                    hGuideSurname.setVisibility(View.INVISIBLE);
                    hGuideDesc.setVisibility(View.INVISIBLE);
                    hGuidePicture.setVisibility(View.INVISIBLE);
                    hGuideRatingBar.setVisibility(View.INVISIBLE);
                    connectViaEmail.setVisibility(View.INVISIBLE);
                    connectViaChat.setVisibility(View.INVISIBLE);
                    finishWithGuide.setVisibility(View.INVISIBLE);
                    hired_guide_bg.setVisibility(View.INVISIBLE);
                    hiredBanner.setVisibility(View.INVISIBLE);

                    hGuideEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}



