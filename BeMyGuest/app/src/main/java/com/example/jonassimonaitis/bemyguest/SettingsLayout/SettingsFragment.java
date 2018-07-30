package com.example.jonassimonaitis.bemyguest.SettingsLayout;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.R;
import com.example.jonassimonaitis.bemyguest.Settings.AboutFragment;
import com.example.jonassimonaitis.bemyguest.Settings.EditProfileFragment;
import com.example.jonassimonaitis.bemyguest.Settings.FeedbackFragment;
import com.example.jonassimonaitis.bemyguest.Settings.NewGuideFragment;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "SettingsFragment";
    private Button newGuide, editProfile, feedBack, about;
    private NewGuideFragment newGuideFragment;
    private EditProfileFragment editProfileFragment;
    private FeedbackFragment feedbackFragment;
    private AboutFragment aboutFragment;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        newGuideFragment = new NewGuideFragment();
        editProfileFragment = new EditProfileFragment();
        feedbackFragment = new FeedbackFragment();
        aboutFragment = new AboutFragment();

        newGuide = view.findViewById(R.id.buttonSettingsNewGuide);
        newGuide.setOnClickListener(this);
        editProfile = view.findViewById(R.id.buttonSettingsEditProfile);
        editProfile.setOnClickListener(this);
        feedBack = view.findViewById(R.id.buttonSettingsSendFeedback);
        feedBack.setOnClickListener(this);
        about = view.findViewById(R.id.buttonSettingsAbout);
        about.setOnClickListener(this);



        return view;

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.settings_frame_layout, fragment).addToBackStack(null);
        fragmentTransaction.commit();


    }


    @Override
    public void onClick(View v) {
        if(v == newGuide){
            setFragment(newGuideFragment);
        }else if(v == editProfile){
            setFragment(editProfileFragment);
        }else if(v == feedBack){
            setFragment(feedbackFragment);
        }else if(v == about){
            setFragment(aboutFragment);
        }
    }


}
