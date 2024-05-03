package com.example.anaghafishapp.Useractivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;


import android.Manifest;

import com.bumptech.glide.load.engine.Resource;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ManagementCart;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import android.telephony.SmsManager;

import java.lang.reflect.TypeVariable;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private ManagementCart managementCart;

    private int cartItemCount = 0;
    private static final int SMS_PERMISSION_REQUEST_CODE = 100;
    private static final String PHONE_NUMBER = "1234567890";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_category) {
                selectedFragment = new CategoryFragment();
            } else if (item.getItemId() == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            loadFragment(selectedFragment);
            return true;
        });

        // Set the default selected item
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        Log.d("MainActivity", "FCM Token: " + token);

                        // Send this token to your server
                        sendTokenToServer(token);
                    } else {
                        Log.e("MainActivity", "Failed to get FCM token");
                    }
                });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, proceed with sending SMS messages
            sendSMS();
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        }

        managementCart = new ManagementCart(this);


    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, proceed with sending SMS messages
                sendSMS();
            } else {
                // Permission is denied, handle the situation accordingly
                // Optionally, you can show a message explaining why the permission is necessary
            }
        }
    }

    private void sendSMS() {
        // Implement your SMS sending logic here
    }


    private void sendTokenToServer(String token) {
        // Send the FCM token to your server using HTTP request or other methods
        // Make sure to associate the token with the user's account on your server
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        // Remove the onBackPressed() method from MainActivity to use the default behavior.
        // Alternatively, you can add your custom logic here if needed.
        super.onBackPressed();
    }


}
