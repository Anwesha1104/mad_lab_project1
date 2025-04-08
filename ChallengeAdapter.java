
package com.example.lab_project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

    private Context context;
    private List<Challenge> challengeList;
    private OnChallengeConfirmedListener listener;

    public interface OnChallengeConfirmedListener {
        void onChallengeConfirmed(Challenge challenge);
    }

    public ChallengeAdapter(Context context, List<Challenge> challengeList, OnChallengeConfirmedListener listener) {
        this.context = context;
        this.challengeList = challengeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChallengeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challenge_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeAdapter.ViewHolder holder, int position) {
        Challenge challenge = challengeList.get(position);
        holder.txtTitle.setText(challenge.getTitle());
        holder.txtDescription.setText(challenge.getDescription());
        holder.imgChallenge.setImageResource(challenge.getImageResId());

        // 👇 On item click: show confirmation dialog
        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Start Challenge")
                    .setMessage("Do you want to start the challenge: \"" + challenge.getTitle() + "\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        listener.onChallengeConfirmed(challenge);
                        Toast.makeText(context, "Challenge added to Dashboard", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription;
        ImageView imgChallenge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgChallenge = itemView.findViewById(R.id.imgChallenge);
        }
    }
}

