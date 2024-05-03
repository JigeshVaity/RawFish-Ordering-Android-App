package com.example.anaghafishapp;

import static android.app.PendingIntent.getActivity;

import static java.security.AccessController.getContext;

import android.util.Log;
import android.widget.Toast;

import com.example.anaghafishapp.CustomerInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// ...

// ...
// ...

public class FirebaseDatabaseHelper {

    private static final String TAG = "FirebaseDatabaseHelper";

    private DatabaseReference databaseReference;

    public FirebaseDatabaseHelper() {
        // Get a reference to your Firebase database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("CustomerInfo"); // Change "CustomerInfo" to your desired node name
    }

    public void uploadCustomerInfo(String fullName, String phoneNo, String address, String street,
                                   String landmark, String pincode, String city, String state) {
        // Get the current authenticated user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Create a unique key for the new customer under the user's ID
            String customerId = currentUser.getUid();

            // Create a CustomerInfo object
            CustomerInfo customerInfo = new CustomerInfo(fullName, phoneNo, address, street,
                    landmark, pincode, city, state);

            // Upload the customer info to Firebase under the user's ID
            databaseReference.child(customerId).setValue(customerInfo)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "CustomerInfo uploaded successfully");

                        } else {
                            Log.e(TAG, "Error uploading CustomerInfo", task.getException());
                          
                        }
                    });
        }
    }
}


