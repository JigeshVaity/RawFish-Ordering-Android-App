package com.example.anaghafishapp.otplogin;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.anaghafishapp.R;

import com.example.anaghafishapp.vendor.VenderActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUsernametwo extends AppCompatActivity {

    EditText usernameInput;
    Button letMeInBtn;
    ProgressBar progressBar;
    String phoneNumber;
    UserModel userModel;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username);

        usernameInput = findViewById(R.id.login_username);
        letMeInBtn = findViewById(R.id.login_let_me_in_btn);
        progressBar = findViewById(R.id.login_progess_bar);

        // Retrieve phone number from the intent
        phoneNumber = getIntent().getStringExtra("mobile1");

        // Reference to the "users" node in the Realtime Database
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        getUsername();

        letMeInBtn.setOnClickListener(v -> setUsername());
    }

    void setUsername() {
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty() || username.length() < 5) {
            usernameInput.setError("Username length should be at least 5 characters");
            return;
        }
        setInProgress(true);

        long currentTime = System.currentTimeMillis();

        if (phoneNumber == null) {
            // Handle the case where phoneNumber is null
            setInProgress(false);
            return;
        }

        if (userModel != null) {
            userModel.setUsername(username);
            userModel.setLoginTime(currentTime);
        } else {
            userModel = new UserModel(phoneNumber, username, currentTime);
        }

        // Save the user data to the Realtime Database
        usersRef.child(phoneNumber).setValue(userModel).addOnCompleteListener(task -> {
            setInProgress(false);
            if (task.isSuccessful()) {
                Intent intent = new Intent(LoginUsernametwo.this, VenderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    void getUsername() {
        setInProgress(true);

        // Read user data from the Realtime Database
        usersRef.child(phoneNumber).get().addOnCompleteListener(task -> {
            setInProgress(false);
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    userModel = snapshot.getValue(UserModel.class);
                    if (userModel != null) {
                        usernameInput.setText(userModel.getUsername());
                    }
                }
            }
        });
    }

    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            letMeInBtn.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            letMeInBtn.setVisibility(View.VISIBLE);
        }
    }
}

