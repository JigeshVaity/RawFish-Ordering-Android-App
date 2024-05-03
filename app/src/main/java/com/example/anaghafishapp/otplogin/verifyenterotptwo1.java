package com.example.anaghafishapp.otplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anaghafishapp.R;
import com.example.anaghafishapp.otplogin.LoginUsername;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyenterotptwo1 extends AppCompatActivity {
    EditText inputotp;
    String getotpbackend;
    TextView textResendOtp;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyenterotptwo);

        inputotp = findViewById(R.id.inputotp);
        TextView textView = findViewById(R.id.textmobileshownumber);
        textView.setText(String.format("+91-%s", getIntent().getStringExtra("mobile1")));
        getotpbackend = getIntent().getStringExtra("backendotp1");

        final ProgressBar progressBar = findViewById(R.id.progress_verify_otp);
        final Button verifybuttonclick = findViewById(R.id.buttonotpsubmit);
        verifybuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = inputotp.getText().toString().trim();
                if (!TextUtils.isEmpty(otp)) {
                    progressBar.setVisibility(View.VISIBLE);
                    verifybuttonclick.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotpbackend, otp);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    verifybuttonclick.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), LoginUsernametwo.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("mobile1", textView.getText().toString());
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(verifyenterotptwo1.this, "Enter correct otp", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(verifyenterotptwo1.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textResendOtp = findViewById(R.id.textresendotp);
        textResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textResendOtp.setEnabled(false);
                startCountdownTimer();
                resendOtp();
            }
        });
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textResendOtp.setText("Resend OTP in " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                textResendOtp.setEnabled(true);
                textResendOtp.setText("RESEND OTP AGAIN");
            }
        };
        countDownTimer.start();
    }

    private void resendOtp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + getIntent().getStringExtra("mobile1"),
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Handle verification completed (auto-retrieval of OTP)
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Handle verification failed
                        Toast.makeText(verifyenterotptwo1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(newbackendotp, forceResendingToken);
                        getotpbackend = newbackendotp;
                        Toast.makeText(verifyenterotptwo1.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
