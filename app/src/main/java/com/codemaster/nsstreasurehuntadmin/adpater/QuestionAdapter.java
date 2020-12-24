package com.codemaster.nsstreasurehuntadmin.adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codemaster.nsstreasurehuntadmin.R;
import com.codemaster.nsstreasurehuntadmin.model.QuestionDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    List<QuestionDetails> questionDetailsList;
    Context context;
    public QuestionAdapter(List<QuestionDetails> questionDetailsList) {
        this.questionDetailsList = questionDetailsList;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_details_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        final QuestionDetails questionDetails = questionDetailsList.get(position);
        holder.questionNumberTex.setText(questionDetails.getqNo());
        holder.checkBox.setChecked(questionDetails.isImgAvl());
        holder.updateBtn.setVisibility(View.GONE);
        final ViewHolder holderSingle = holder;
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                questionDetails.setImgAvl(true);
            } else {
                questionDetails.setImgAvl(false);
            }
            holderSingle.updateBtn.setVisibility(View.VISIBLE);
        });
        holder.updateBtn.setOnClickListener(v -> {
            //Initialize Firebase
            final DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Questions").child(questionDetails.getqNo()).setValue(questionDetails)
                    .addOnSuccessListener(aVoid -> {
                        holderSingle.updateBtn.setVisibility(View.GONE);
                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return questionDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionNumberTex;
        CheckBox checkBox;
        Button updateBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNumberTex = itemView.findViewById(R.id.qno);
            checkBox = itemView.findViewById(R.id.hintPublished);
            updateBtn = itemView.findViewById(R.id.update);
        }
    }
}
