package com.example.anaghafishapp.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.login.LoginActivity;
import com.example.anaghafishapp.otplogin.entermobilenumberone;
import com.example.anaghafishapp.otplogin.entermobilenumberone1;

public class registerpage extends AppCompatActivity {

    Button b1;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(R.layout.activity_registerpage);

        b1 = findViewById(R.id.button1);
        layout = findViewById(R.id.registerPageLayout); // assuming you have a LinearLayout or another layout as the root

        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView);
        animationView.setAnimation("background.json");
        animationView.loop(true);
        animationView.playAnimation();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerpage.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        // Create a GradientDrawable with rounded corners for the first button
        GradientDrawable gradientDrawable1 = new GradientDrawable();
        gradientDrawable1.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable1.setCornerRadius(20f); // Adjust the radius as needed
        gradientDrawable1.setColor(Color.parseColor("#04C4FC")); // Initial color

// Set the background drawable for the first button
        b1.setBackground(gradientDrawable1);

// Create an ObjectAnimator for color animation for the first button
        ObjectAnimator colorAnimator1 = ObjectAnimator.ofInt(gradientDrawable1, "color", Color.parseColor("#04C4FC"), Color.parseColor("#C9FF72"));
        colorAnimator1.setDuration(4000);
        colorAnimator1.setEvaluator(new ArgbEvaluator());
        colorAnimator1.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimator1.setRepeatCount(ValueAnimator.INFINITE);
// Start the color animation for the first button
        colorAnimator1.start();


    }
}
