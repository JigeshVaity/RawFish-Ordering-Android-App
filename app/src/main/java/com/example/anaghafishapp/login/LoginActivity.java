package com.example.anaghafishapp.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.Useractivity.MainActivity;
import com.example.anaghafishapp.vendor.VenderActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.sign_in_email);
        passwordEditText = findViewById(R.id.sign_in_password);
        Button switchButton1 = findViewById(R.id.sign_up_button_user);
        Button switchButton2 = findViewById(R.id.sign_up_button_vender);
        Button signInButton = findViewById(R.id.sign_in_button);

        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView1);
        animationView.setAnimation("login.json"); // Set the animation file
        animationView.loop(true); // Set the animation to loop
        // Start the animation
        animationView.playAnimation();

        EditText passwordEditText = findViewById(R.id.sign_in_password);
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable[] drawables = passwordEditText.getCompoundDrawables();

                    if (drawables.length > DRAWABLE_RIGHT && drawables[DRAWABLE_RIGHT] != null
                            && event.getRawX() >= (passwordEditText.getRight() - drawables[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        togglePasswordVisibility(passwordEditText);
                        return true;
                    }
                }
                return false;
            }
        });


        switchButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to switch to CustomerLogin
                Intent intent = new Intent(LoginActivity.this, CustomerLogin.class);
                startActivity(intent);
            }
        });

        switchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to switch to VendorLogin
                Intent intent = new Intent(LoginActivity.this, VendorLogin.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Check if email and password are not empty
                if (!email.isEmpty() && !password.isEmpty()) {
                    // Check if the user is a customer
                    checkCustomerLogin(email, password);

                    // Check if the user is a vendor
                    checkVendorLogin(email, password);

                } else {
                    // Display a toast if email or password is empty
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkCustomerLogin(final String email, final String password) {
        FirebaseDatabase.getInstance().getReference("SignupCustomer")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // User found in SignupCustomer node
                            // Now, check the password
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                UserVendor user = userSnapshot.getValue(UserVendor.class);
                                if (user != null && user.getPassword().equals(hashPassword(password))) {
                                    // Password matches, login as customer
                                    Toast.makeText(LoginActivity.this, "Logging in as customer: " + email, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    // Add your logic to navigate to customer activity
                                } else {
                                    // Password doesn't match
                                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // User not found in SignupCustomer node
                            // You can check vendor login here if needed
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                        Toast.makeText(LoginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkVendorLogin(final String email, final String password) {
        FirebaseDatabase.getInstance().getReference("SignupVendor")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // User found in SignupVendor node
                            // Now, check the password
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                UserVendor user;
                                user = userSnapshot.getValue(UserVendor.class);
                                if (user != null && user.getPassword().equals(hashPassword(password))) {
                                    // Password matches, login as vendor
                                    Toast.makeText(LoginActivity.this, "Logging in as vendor: " + email, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, VenderActivity.class);
                                    startActivity(intent);
                                    finish();
                                    // Add your logic to navigate to vendor activity
                                } else {
                                    // Password doesn't match
                                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // User not found in SignupVendor node
                            // You can handle other cases or show an appropriate message
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                        Toast.makeText(LoginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String hashPassword(String password) {
        // Implement your password hashing logic here
        // This is a basic example using SHA-256, but you should use a more secure algorithm
        // and consider adding salt for additional security

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(hash, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void togglePasswordVisibility(EditText passwordEditText) {
        int selection = passwordEditText.getSelectionEnd();

        Drawable leftDrawable;
        Drawable rightDrawable;

        if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            // Show password
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            leftDrawable = getResources().getDrawable(R.drawable.password);
            rightDrawable = getResources().getDrawable(R.drawable.eyeclose2);
            passwordEditText.setTypeface(Typeface.DEFAULT);
        } else {
            // Hide password
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            leftDrawable = getResources().getDrawable(R.drawable.password);
            rightDrawable = getResources().getDrawable(R.drawable.eye);
            passwordEditText.setTypeface(Typeface.DEFAULT);
        }

        // Set drawables
        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, rightDrawable, null);

        // Restore cursor position
        passwordEditText.setSelection(selection);
    }



    // Set the drawableLeft icon explicitly
        //passwordEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, 0, 0);

        // Set the font family explicitly
        //passwordEditText.setTypeface(Typeface.DEFAULT);




}
