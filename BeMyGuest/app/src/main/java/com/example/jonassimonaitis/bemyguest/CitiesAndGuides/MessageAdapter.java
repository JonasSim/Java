package com.example.jonassimonaitis.bemyguest.CitiesAndGuides;

import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Messages> mMessageList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    public MessageAdapter(List<Messages> mMessageList){
        this.mMessageList = mMessageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);


        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends  RecyclerView.ViewHolder {

        public TextView messageText, messageFrom;
        public CircleImageView profileImage;

        public MessageViewHolder(View view){
            super(view);

            messageFrom = view.findViewById(R.id.name_text_layout);
            messageText = view.findViewById(R.id.message_text_layout);
            profileImage = view.findViewById(R.id.message_profile_layout);

        }

    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i){

        mAuth = FirebaseAuth.getInstance();
        String current_user_id = mAuth.getCurrentUser().getUid();


        Messages c = mMessageList.get(i);


        String from_user = c.getFrom();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("photoURL").getValue().toString();

                viewHolder.messageFrom.setText(name);

                Picasso.with(viewHolder.profileImage.getContext()).load(image)
                        .placeholder(R.drawable.defaulticon).into(viewHolder.profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(from_user.equals(current_user_id)){


        }else{



        }


        viewHolder.messageText.setText(c.getMessage());
    }

    @Override
    public int getItemCount(){
        return mMessageList.size();
    }



}
