package com.example.jonassimonaitis.bemyguest;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {


    ImageView logo;
    Animation fromTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        logo = findViewById(R.id.logoIntro);

        fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        logo.setAnimation(fromTop);

        Window window = getWindow();

        //Change status bar color
        //M for MarshMallow
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.setStatusBarColor(getColor(R.color.mystatusbarColorIntro));
        }else{
            window.setStatusBarColor(getResources().getColor(R.color.mystatusbarColorIntro));
        }

        //Create Intent Object
        final Intent i = new Intent(this, WelcomeActivity.class);

        //Create thread to change Activity automaticly after time is over..
        Thread timer = new Thread(){
            public void run(){
                try{
                    //Intro time
                    sleep(3000);
                    startActivity(i);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        //Start timer...
        timer.start();

    }
}
