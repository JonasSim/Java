package com.example.jonassimonaitis.bemyguest.MenuFragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.CitiesAndGuides.CitiesActivity;
import com.example.jonassimonaitis.bemyguest.Post;
import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> mPostViewAdapter;
    private DatabaseReference mDataRef, mDatabaseLikesRef;
    private FirebaseAuth mAuth;
    private EditText searchField;
    private ImageButton searchButton;
    private String current_user_id;
    private boolean mProccessLike = false;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        //Database
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        mDatabaseLikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");

        searchField = view.findViewById(R.id.search_field);
        searchButton = view.findViewById(R.id.imageButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search_field = searchField.getText().toString().trim();
                firebaseSearch(view, search_field);

            }
        });

        String search_field = null;
        initialiseScreen(view, search_field);


        return  view;

    }

    private void sendPostToFirebase() {

        Post post = new Post();
        String UID = Utils.getUID();
        post.setImageURL("https://firebasestorage.googleapis.com/v0/b/bemyguest-d870e.appspot.com/o/postImages%2FslideAlicante.jpg?alt=media&token=bba48e71-a6cd-4692-ab5a-69066d2b1cfe");
        post.setmUid(UID);
        post.setPostName("Test");
        post.setLatitude("34.54");
        post.setLongitude("11.43");

        mDataRef.child(UID).setValue(post);
    }


    private void firebaseSearch(final View view, final String searchText){

        try{

            Query firebaseSearchQuery = mDataRef.orderByChild("postName").startAt(searchText).endAt(searchText + "\uf8ff");
            String aa = firebaseSearchQuery.toString();

            firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){

                        Query postQuery;

                        postQuery = mDataRef.orderByChild("postName").startAt(searchText).endAt(searchText + "\uf8ff");

                        mDataRef.keepSynced(true);

                        recyclerView = view.findViewById(R.id.post_RV);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(mPostViewAdapter);

                        FirebaseRecyclerOptions postOptions = new FirebaseRecyclerOptions.Builder<Post>()
                                .setQuery(postQuery, Post.class).build();

                        mPostViewAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(postOptions) {
                            @Override
                            protected void onBindViewHolder(PostViewHolder holder, int position, final Post model) {

                                final String post_key = getRef(position).getKey();
                                holder.setPostCityImage(model.getImageURL());
                                holder.setPostCityName(model.getPostName());
                                holder.setLikeBtn(post_key);
                                //When is clicked once go to city fragment
                                holder.cityImg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Add code here

                                        Intent singlePostIntent = new Intent(getActivity(), CitiesActivity.class);
                                        singlePostIntent.putExtra("blog_id", post_key);
                                        startActivity(singlePostIntent);

                                    }
                                });

                                //Likes button
                                holder.likes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mProccessLike = true;

                                        String postId = model.getmUid();


                                        mDatabaseLikesRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                if(mProccessLike) {

                                                    if (dataSnapshot.child(post_key).hasChild(current_user_id)) {


                                                        mDatabaseLikesRef.child(post_key).child(current_user_id).removeValue();
                                                        mProccessLike = false;

                                                    } else {

                                                        mDatabaseLikesRef.child(post_key).child(current_user_id).setValue("RandomValue");
                                                        mProccessLike = false;

                                                    }

                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });

                                if(searchButton == view){
                                    mPostViewAdapter.stopListening();
                                }
//

                            }

                            @Override
                            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_post, parent, false);

                                return new PostViewHolder(view);
                            }
                        };

                        recyclerView.setAdapter(mPostViewAdapter);
                        mPostViewAdapter.startListening();



                    }else{
                        Toast.makeText(getContext(), "There is no such a City. Try again!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }catch (NullPointerException e){

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    private void initialiseScreen(final View view, String searchText) {


        Query postQuery;

        if(TextUtils.isEmpty(searchText)){
            postQuery = mDataRef.orderByChild("postName");
        }else{
            postQuery = mDataRef.orderByChild("postName").startAt(searchText).endAt(searchText + "\uf8ff");
        }

        mDataRef.keepSynced(true);

        recyclerView = view.findViewById(R.id.post_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mPostViewAdapter);

        FirebaseRecyclerOptions postOptions = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(postQuery, Post.class).build();

        mPostViewAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(postOptions) {
            @Override
            protected void onBindViewHolder(PostViewHolder holder, int position, final Post model) {



               final String post_key = getRef(position).getKey();
               holder.setPostCityImage(model.getImageURL());
               holder.setPostCityName(model.getPostName());
               holder.setLikeBtn(post_key);
               //When is clicked once go to city fragment
               holder.cityImg.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //Add code here

                       Intent singlePostIntent = new Intent(getActivity(), CitiesActivity.class);
                       singlePostIntent.putExtra("blog_id", post_key);
                       startActivity(singlePostIntent);

                   }
               });

               //Likes button
              holder.likes.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      mProccessLike = true;

                      String postId = model.getmUid();


                          mDatabaseLikesRef.addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(DataSnapshot dataSnapshot) {

                                  if(mProccessLike) {

                                      if (dataSnapshot.child(post_key).hasChild(current_user_id)) {


                                          mDatabaseLikesRef.child(post_key).child(current_user_id).removeValue();
                                          mProccessLike = false;

                                      } else {

                                          mDatabaseLikesRef.child(post_key).child(current_user_id).setValue("RandomValue");
                                          mProccessLike = false;

                                      }

                                  }

                              }

                              @Override
                              public void onCancelled(DatabaseError databaseError) {

                              }
                          });
                  }
              });

              if(searchButton == view){
                  mPostViewAdapter.stopListening();
              }
//

            }

            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_post, parent, false);

                return new PostViewHolder(view);
            }
        };

        recyclerView.setAdapter(mPostViewAdapter);
    }




    public class PostViewHolder extends RecyclerView.ViewHolder {


        ImageView likes, cityImg;
        TextView postCityName, likesnumb;

        DatabaseReference mDataLikes;
        FirebaseAuth mAuth;

         PostViewHolder(View itemView) {
            super(itemView);

            cityImg = itemView.findViewById(R.id.postCityImageURL);
            likes = itemView.findViewById(R.id.like_drawable);
            postCityName = itemView.findViewById(R.id.postCityName);
            mAuth = FirebaseAuth.getInstance();
            mDatabaseLikesRef.keepSynced(true);

        }

        public void setLikeBtn(final String post_key){

            final String current_user = mAuth.getCurrentUser().getUid();

             mDatabaseLikesRef.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {

                     if(dataSnapshot.child(post_key).hasChild(current_user)){

                         likes.setImageResource(R.drawable.ic_favorite_red_24dp);

                     }else{

                         likes.setImageResource(R.drawable.ic_favorite_borderunchecked_black_24dp);

                     }

                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });

        }

        public void setPostCityImage(final String imageURL){

            Picasso.with(getContext()).load(imageURL).networkPolicy(NetworkPolicy.OFFLINE).into(cityImg, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(getActivity()).load(imageURL).into(cityImg);
                }
            });

        }

        public void setPostCityName(String name){

            postCityName.setText(String.valueOf(name));
        }

        public void setNumLikes(long num){
            likesnumb.setText(String.valueOf(num));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mPostViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPostViewAdapter.stopListening();
    }
}
