package com.example.jonassimonaitis.bemyguest.SettingsLayout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.jonassimonaitis.bemyguest.R;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // [STARTS - Status bar transparency]

        changeStatusBarColor();

        // [ENDS - Status bar]

        Fragment settingsFragment = new SettingsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.settings_frame_layout, settingsFragment).addToBackStack(null).commit();

        toolbar = findViewById(R.id.settings_tool_bar);
        setSupportActionBar(toolbar);
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Back");

    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getFragmentManager().getBackStackEntryCount() == 1) {
                    finish();
                }else{
                    super.onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //If user is in Settings Fragment and presses back button it will go to MainAcitivty Profile Fragment
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }else{
            super.onBackPressed();
        }

    }
}
