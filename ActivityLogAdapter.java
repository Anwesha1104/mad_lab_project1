package com.example.lab_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.ViewHolder> {
    private List<ActivityLog> activityLogs;

    public ActivityLogAdapter(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActivityLog activityLog = activityLogs.get(position);
        holder.textActivityName.setText(activityLog.getActivityName());
        holder.textDate.setText(activityLog.getDate());
    }

    @Override
    public int getItemCount() {
        return activityLogs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textActivityName, textDate;

        public ViewHolder(View itemView) {
            super(itemView);
            textActivityName = itemView.findViewById(R.id.textActivityName);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }
}
