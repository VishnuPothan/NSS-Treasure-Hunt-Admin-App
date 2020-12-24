package com.codemaster.nsstreasurehuntadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codemaster.nsstreasurehuntadmin.adpater.QuestionAdapter;
import com.codemaster.nsstreasurehuntadmin.model.QuestionDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HintPublish extends AppCompatActivity {

    private DatabaseReference mDatabase;
    RecyclerView questionRecycler;
    QuestionAdapter questionAdapter;
    ProgressBar progressBar;
    List<QuestionDetails> questionDetailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint_publish);

        //initialize
        questionRecycler = findViewById(R.id.questionRecycler);
        progressBar = findViewById(R.id.progressBar);

        //Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadQuestionDetails();
    }

    private void loadQuestionDetails() {
        progressBar.setVisibility(View.VISIBLE);
        mDatabase.child("Questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    QuestionDetails questionDetails = postSnapshot.getValue(QuestionDetails.class);
                    questionDetailsList.add(questionDetails);
                }
                progressBar.setVisibility(View.GONE);
                questionAdapter = new QuestionAdapter(questionDetailsList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                questionRecycler.setLayoutManager(layoutManager);
                questionRecycler.setAdapter(questionAdapter);
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Some error occurred, restart the hunt", Toast.LENGTH_SHORT).show();
            }
        });
    }
}