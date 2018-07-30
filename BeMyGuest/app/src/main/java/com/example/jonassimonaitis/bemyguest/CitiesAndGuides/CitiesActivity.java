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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.MapSupportFragment;
import com.example.jonassimonaitis.bemyguest.MapsActivity;
import com.example.jonassimonaitis.bemyguest.Post;
import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.UserModelClass;
import com.example.jonassimonaitis.bemyguest.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.FormatFlagsConversionMismatchException;

import de.hdodenhof.circleimageview.CircleImageView;

public class CitiesActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String TAG = "CityActivity";
    private ImageView cityImageView;
    private ScrollView scrollView;
    private Toolbar toolbar;
    private GoogleMap googleMap;
    private TextView ratingNumbTV, cityName;
    private RatingBar cityRatingBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDataRef, guideDataRef;
    private Double latitude, longitude;
    private Post post;
    private String post_key = null;
    public String uID;

    //Guide RecyclerView

    private RecyclerView guideRecyclerView;
    private FirebaseRecyclerAdapter<UserModelClass, guideViewHolder> guideAdapter;

    public CitiesActivity() {

    }

    //RecylcerView END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        //Firebase part
        FirebaseAuth.getInstance();
        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        guideDataRef = FirebaseDatabase.getInstance().getReference().child("Guides");
        post_key = getIntent().getExtras().getString("blog_id");

        scrollView = findViewById(R.id.sv_container);
        ((MapSupportFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map)).getMapAsync(this);
        scrollView = findViewById(R.id.sv_container);
        cityName = findViewById(R.id.cityNameTV);
        ratingNumbTV = findViewById(R.id.ratingNumberTV);
        cityRatingBar = findViewById(R.id.cityRatingBar);
        cityImageView = findViewById(R.id.cityImageView);
        toolbar = findViewById(R.id.city_tool_bar);
        setSupportActionBar(toolbar);

        guideRecyclerView = findViewById(R.id.guide_RecylcerView);
        guideRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        guideRecyclerView.setAdapter(guideAdapter);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Back");

        changeStatusBarColor();


        ((MapSupportFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map)).setListener(new MapSupportFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        //Rating bar
        cityRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String resultStr = getString(R.string.ratingString);
                ratingNumbTV.setText(resultStr + " " + rating);
            }
        });

        mDataRef.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String cityNames = (String) dataSnapshot.child("postName").getValue();
                String cityImages = (String) dataSnapshot.child("imageURL").getValue();
                latitude = Double.parseDouble(dataSnapshot.child("latitude").getValue().toString());
                longitude = Double.parseDouble(dataSnapshot.child("longitude").getValue().toString());

                cityName.setText(cityNames);
                Picasso.with(getApplicationContext()).load(cityImages).into(cityImageView);

                LatLng cityLocation = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(cityLocation).title(cityNames));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(cityLocation));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 10));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d(TAG, databaseError.toString());
            }
        });

        recycler();



    }   // onCreated END HERE -------------------------------------

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;


    }

    //Start of Recycler View -----------------


    public void recycler() {
        super.onStart();

        try {
            //Guide RecyclerView
            Query guideQuery = guideDataRef.orderByKey();
            guideQuery.keepSynced(true);

            FirebaseRecyclerOptions guideOptions =
                    new FirebaseRecyclerOptions.Builder<UserModelClass>().setQuery(guideQuery, UserModelClass.class).build();

            guideAdapter = new FirebaseRecyclerAdapter<UserModelClass, guideViewHolder>(guideOptions) {


                @Override
                protected void onBindViewHolder(@NonNull guideViewHolder holder, final int position, @NonNull final UserModelClass model) {

                    String pickedcity = model.getPickedCity();
                    String postname = (String) cityName.getText();

                    if(pickedcity.equals(postname)) {

                        final String guide_key= getRef(position).getKey();

                        holder.setGuideName(model.getName());
                        holder.setGuideSurname(model.getSurName());
                        holder.setGuideImage(getApplicationContext(), model.getPhotoURL());
                        holder.mView.setVisibility(View.VISIBLE);

                        //Guide Click listener
                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent guideHireIntent = new Intent(getApplication(), GuideHireActivity.class);
                                guideHireIntent.putExtra("guide_id", guide_key);
                                startActivity(guideHireIntent);
                                finish();

                            }
                        });

                    }

                }

                @NonNull
                @Override
                public guideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_guides, parent, false);
                    return new guideViewHolder(view);
                }

                @Override
                public void onError(DatabaseError e){
                    Toast.makeText(getApplicationContext(), "Error by stopping ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public int getItemCount() {
                    return super.getItemCount();
                }

                @Override
                public void onDataChanged() {
                    super.onDataChanged();
                    notifyDataSetChanged();
                }
            };

            guideAdapter.notifyDataSetChanged();
            guideRecyclerView.setAdapter(guideAdapter);
            guideAdapter.startListening();


        } catch (DatabaseException e) {

            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        if(guideAdapter != null){
            guideAdapter.stopListening();
        }
    }


    public static class guideViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public guideViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mView.setVisibility(View.GONE);
        }

        public void setGuideName(String name) {
            TextView guideName = mView.findViewById(R.id.guideNameTV);
            guideName.setText(name);
        }

        public void setGuideSurname(String surname) {
            TextView guideSurname = mView.findViewById(R.id.guideSurnameTV);
            guideSurname.setText(surname);
        }

        public void setGuideImage(Context ctx, String image) {
            CircleImageView guideImage = mView.findViewById(R.id.guidePicture);
            Picasso.with(ctx).load(image).placeholder(R.drawable.defaulticon).into(guideImage);
        }

    }



} // [END OF ACTIVITY]
