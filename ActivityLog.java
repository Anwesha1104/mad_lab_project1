package com.example.lab_project;

public class ActivityLog {
    private String activityName;
    private String date;

    public ActivityLog(String activityName, String date) {
        this.activityName = activityName;
        this.date = date;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getDate() {
        return date;
    }
}
