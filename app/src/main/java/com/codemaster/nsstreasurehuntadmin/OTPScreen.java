package com.codemaster.nsstreasurehuntadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.codemaster.nsstreasurehuntadmin.SharedPreference.SharedPreference;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OTPScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    String verificationCodeBySystem;
    String phoneNumberStr;
    Button verifyBtn;
    PinView OTPCodePinView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_screen);

        //initialization
        verifyBtn = findViewById(R.id.verifyBtn);
        OTPCodePinView = findViewById(R.id.pinView);
        progressBar = findViewById(R.id.progressBar);

        //get intent values
        phoneNumberStr = getIntent().getStringExtra("PhoneNumber");

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        sendVerificationCode(phoneNumberStr);

        //verifyBtn click listener
        verifyBtn.setOnClickListener(v -> {
            String code = OTPCodePinView.getText().toString();
            if (code.isEmpty() || code.length() < 6) {
                OTPCodePinView.setError("Wrong OTP..");
                OTPCodePinView.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            verifyCode(code);
        });
    }

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredential(credential);
    }

    private void signInTheUserByCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(com.codemaster.nsstreasurehuntadmin.OTPScreen.this, task -> {
            if (task.isSuccessful()) {
                checkExistingUser();
            } else {
                Toast.makeText(com.codemaster.nsstreasurehuntadmin.OTPScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkExistingUser() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        mDatabase.child(phoneNumberStr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Intent mainIntent = new Intent(getApplicationContext(), com.codemaster.nsstreasurehuntadmin.HomeScreen.class);
                    SharedPreference.setUserVerified(getApplicationContext(), true);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Your are not a valid user",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Your are not a valid user!!!",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void sendVerificationCode(String phoneNo) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Initialize phone auth callbacks
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            String code = credential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(com.codemaster.nsstreasurehuntadmin.OTPScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("here",e.getMessage());
        }
    };
}