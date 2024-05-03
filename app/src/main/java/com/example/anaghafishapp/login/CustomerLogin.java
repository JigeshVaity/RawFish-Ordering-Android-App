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
import com.example.anaghafishapp.otplogin.entermobilenumberone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerLogin extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signUpButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.sign_in_email);
        passwordEditText = findViewById(R.id.sign_in_password);
        signUpButton = findViewById(R.id.sign_in_button);
        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView2);
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
        Button switchButton = findViewById(R.id.back_customer_to_login);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to switch to LoginActivity
                Intent intent = new Intent(CustomerLogin.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button switchButton2 = findViewById(R.id.back_customer_to_otp);
        switchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to switch to LoginActivity
                Intent intent = new Intent(CustomerLogin.this, entermobilenumberone.class);
                startActivity(intent);
            }
        });


        // Set click listener for sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Check if email and password are not empty
                if (!email.isEmpty() && !password.isEmpty()) {
                    // Sign up with email and password
                    signUpWithEmailAndPassword(email, password);
                } else {
                    // Display a toast if email or password is empty
                    Toast.makeText(CustomerLogin.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to sign up with email and password using Firebase Auth
    private void signUpWithEmailAndPassword(final String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            // Store user data in Realtime Database
                            // Call storeUserDataInDatabase with userId, email, and password
                            storeUserDataInDatabase(userId, email, password);



                            Intent intent = new Intent(CustomerLogin.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(CustomerLogin.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            // You can add code to navigate to the next activity or perform other actions
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(CustomerLogin.this, "Registration failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Method to store user data in Realtime Database
    private void storeUserDataInDatabase(String userId, String email, String password) {
        // Hash the password before storing it
        String hashedPassword = hashPassword(password);

        // Create a UserVendor object with email, additional info, and hashed password
        UserVendor user = new UserVendor(email, "Info", hashedPassword);

        // Store user data in the "SignupVendor" node using userId as the key
        FirebaseDatabase.getInstance().getReference("SignupCustomer").child(userId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // User data stored successfully
                            Toast.makeText(CustomerLogin.this, "User data stored in Realtime Database", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // Error storing user data
                            Toast.makeText(CustomerLogin.this, "Error storing user data: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Hash the password using a secure hashing algorithm (e.g., bcrypt, SHA-256)
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


}
