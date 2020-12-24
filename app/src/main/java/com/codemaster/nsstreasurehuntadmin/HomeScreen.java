package com.codemaster.nsstreasurehuntadmin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeScreen extends AppCompatActivity {
    private DatabaseReference mDatabase;
    CardView hintCard, rankCard, startCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // initialization
        hintCard = findViewById(R.id.hint);
        startCard = findViewById(R.id.time);
        rankCard = findViewById(R.id.rank);

        hintCard.setOnClickListener(v -> {
            Intent hintIntent = new Intent(getApplicationContext(), HintPublish.class);
            startActivity(hintIntent);
        });

        startCard.setOnClickListener(v -> {
            //Initialize Firebase
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("start").child("start").setValue(true).addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show());
        });

        rankCard.setOnClickListener(v -> {
            Intent rankViewIntent = new Intent(getApplicationContext(), RankView.class);
            startActivity(rankViewIntent);
        });
    }
}