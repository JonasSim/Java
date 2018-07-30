package com.example.jonassimonaitis.bemyguest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.jonassimonaitis.bemyguest.MenuFragments.ChatFragment;
import com.example.jonassimonaitis.bemyguest.MenuFragments.GuideFragment;
import com.example.jonassimonaitis.bemyguest.MenuFragments.HomeFragment;
import com.example.jonassimonaitis.bemyguest.MenuFragments.ProfileFragment;

public class  MainActivity extends AppCompatActivity {


    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private GuideFragment guideFragment;
    private ChatFragment chatFragment;
    private ProfileFragment profileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainNav = findViewById(R.id.mainNavigation);
        mMainFrame = findViewById(R.id.mainframeLayout);

        homeFragment = new HomeFragment();
        guideFragment = new GuideFragment();
        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();

        changeStatusBarColor();
        setFragment(homeFragment);


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.navHome:
                        setFragment(homeFragment);
                        return true;

                    case R.id.navGuide:
                        setFragment(guideFragment);
                        return true;

                    case R.id.navChat:
                        setFragment(chatFragment);
                        return true;

                    case R.id.navProfile:
                        setFragment(profileFragment);
                        return true;

                    default:
                        return false;

                }
            }
        });
    }



    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframeLayout, fragment, "Fragment" );
        fragmentTransaction.commit();


    }

    private void setActivity(Activity activity){
        Intent intent = new Intent();

    }
}
