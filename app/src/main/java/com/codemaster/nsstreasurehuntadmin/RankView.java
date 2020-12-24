package com.codemaster.nsstreasurehuntadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.codemaster.nsstreasurehuntadmin.adpater.RankViewAdapter;
import com.codemaster.nsstreasurehuntadmin.model.OnGoingDetail;
import com.codemaster.nsstreasurehuntadmin.model.OnGoingDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankView extends AppCompatActivity {

    private DatabaseReference mDatabase;
    RecyclerView rankRecycler;
    RankViewAdapter rankViewAdapter;
    ProgressBar progressBar;
    List<OnGoingDetails> onGoingDetailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_view);

        //initialize
        rankRecycler = findViewById(R.id.rankRecycler);
        progressBar = findViewById(R.id.progressBar);

        //Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadDataOfOngoing();
    }

    private void loadDataOfOngoing() {
        progressBar.setVisibility(View.VISIBLE);
        mDatabase.child("ongoing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    OnGoingDetail onGoingDetail = postSnapshot.getValue(OnGoingDetail.class);
                    Log.i("here date", onGoingDetail.getDate());
                    onGoingDetailsList.add(new OnGoingDetails(postSnapshot.getKey(), onGoingDetail));
                }
                Collections.sort(onGoingDetailsList);
                Collections.reverse(onGoingDetailsList);
                progressBar.setVisibility(View.GONE);
                rankViewAdapter = new RankViewAdapter(onGoingDetailsList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rankRecycler.setLayoutManager(layoutManager);
                rankRecycler.setAdapter(rankViewAdapter);
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}