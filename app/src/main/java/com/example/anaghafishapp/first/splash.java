package com.example.anaghafishapp.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.Useractivity.MainActivity;
import com.example.anaghafishapp.vendor.VenderActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView2);
        animationView.setAnimation("Logoanimation.json"); // Set the animation file

        animationView.loop(true); // Set the animation to loop

        // Start the animation
        animationView.playAnimation();



       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseUtil.isLoggedIn()){
                    startActivity(new Intent(splash.this, MainActivity.class));
                }
                else if(FirebaseUtiltwo.isLoggedIn()){
                    startActivity(new Intent(splash.this, VenderActivity.class));
                }
                else if(FirebaseUtilthree.isLoggedIn()){
                    startActivity(new Intent(splash.this, MainActivity.class));
                }
                else if(FirebaseUtilfour.isLoggedIn()){
                    startActivity(new Intent(splash.this, VenderActivity.class));
                }
                else {
                    startActivity(new Intent(splash.this,registerpage.class));
                }

                finish();
            }
        },2500);
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseUtil.isLoggedIn()) {
                    startActivity(new Intent(splash.this, MainActivity.class));
                }

                if (FirebaseUtiltwo.isLoggedIn()) {
                    startActivity(new Intent(splash.this, VenderActivity.class));
                }

                if (FirebaseUtilthree.isLoggedIn()) {
                    startActivity(new Intent(splash.this, MainActivity.class));
                }

                if (FirebaseUtilfour.isLoggedIn()) {
                    startActivity(new Intent(splash.this, VenderActivity.class));
                }

                if (!FirebaseUtil.isLoggedIn() && !FirebaseUtiltwo.isLoggedIn() && !FirebaseUtilthree.isLoggedIn() && !FirebaseUtilfour.isLoggedIn()) {
                    startActivity(new Intent(splash.this, registerpage.class));
                }

                finish();
            }
        }, 2500);*/
    }
}