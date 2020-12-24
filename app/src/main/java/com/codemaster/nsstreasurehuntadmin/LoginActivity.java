package com.codemaster.nsstreasurehuntadmin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText mobileNumberTextInput;
    Button sendOTPBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialization
        mobileNumberTextInput = findViewById(R.id.mobileNumberTextInput);
        sendOTPBtn = findViewById(R.id.sentOTPBtn);

        //sendOtp button clicked listener
        sendOTPBtn.setOnClickListener(v -> {
            String phoneNo = mobileNumberTextInput.getText().toString();
            if (phoneNo.isEmpty() || phoneNo.length() < 10) {
                mobileNumberTextInput.setError("Enter valid mobile number");
                mobileNumberTextInput.requestFocus();
                return;
            }
            Intent OTPScreenIntent = new Intent(getApplicationContext(), OTPScreen.class);
            OTPScreenIntent.putExtra("PhoneNumber", "+91" + mobileNumberTextInput.getText().toString());
            startActivity(OTPScreenIntent);
            finish();
        });
    }
}