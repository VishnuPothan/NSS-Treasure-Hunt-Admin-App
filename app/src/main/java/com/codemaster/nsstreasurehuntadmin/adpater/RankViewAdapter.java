package com.codemaster.nsstreasurehuntadmin.adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codemaster.nsstreasurehuntadmin.R;
import com.codemaster.nsstreasurehuntadmin.model.OnGoingDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class RankViewAdapter extends RecyclerView.Adapter<RankViewAdapter.ViewHolder> {
    List<OnGoingDetails> onGoingDetailsList;
    Context context;

    public RankViewAdapter(List<OnGoingDetails> onGoingDetailsList) {
        this.onGoingDetailsList = onGoingDetailsList;
    }

    @NonNull
    @Override
    public RankViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_view_card, parent, false);
        return new RankViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewAdapter.ViewHolder holder, int position) {
        final OnGoingDetails onGoingDetails = onGoingDetailsList.get(position);

        // get name of the user
        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(onGoingDetails.getUserId()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                  Log.i("here name", String.valueOf(snapshot.getValue(String.class)));
                  holder.nameText.setText(snapshot.getValue(String.class));
                } else {
                    Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Some error occurred!!!", Toast.LENGTH_SHORT).show();
            }
        });

        //other details update
        holder.rankText.setText(String.valueOf(position + 1));
        holder.timeText.setText(String.valueOf(onGoingDetails.getTime()));
        holder.questionNo.setText(onGoingDetails.getCurrQno());
    }

    @Override
    public int getItemCount() {
        return onGoingDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rankText, nameText, questionNo, timeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rankText = itemView.findViewById(R.id.rankText);
            nameText = itemView.findViewById(R.id.nameText);
            questionNo = itemView.findViewById(R.id.questionNoText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}
