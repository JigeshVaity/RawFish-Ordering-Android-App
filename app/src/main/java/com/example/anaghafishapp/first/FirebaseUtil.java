package com.example.anaghafishapp.first;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn() {
        return currentUserId() != null;
    }

    public static DatabaseReference currentUserDetail() {
        // Assuming you have a "users" node in your Realtime Database
        return FirebaseDatabase.getInstance().getReference("Login/customer").child(currentUserId());
    }
}
