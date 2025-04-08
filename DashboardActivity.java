package com.example.lab_project;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> requestPermissionLauncher;
    private RecyclerView recyclerDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setupPermissionLauncher();
        createNotificationChannel();
        checkNotificationPermission();


        recyclerDashboard = findViewById(R.id.recyclerDashboard);
        recyclerDashboard.setLayoutManager(new LinearLayoutManager(this));

        List<Challenge> dashboardList = DashboardManager.getDashboardList();

        ChallengeAdapter adapter = new ChallengeAdapter(this, dashboardList, null); // No click listener in dashboard
        recyclerDashboard.setAdapter(adapter);
    }

    public void openLeaderboard(View view) {
        startActivity(new Intent(this, LeaderboardActivity.class));
    }

    public void openProgressReport(View view) {
        startActivity(new Intent(this, ProgressReportActivity.class));
    }

    public void openTips(View view) {
        startActivity(new Intent(this, TipsActivity.class));
    }

    public void openAIMap(View view) {
        startActivity(new Intent(this, AIMapActivity.class));
    }

    public void openActivityLogger(View view) {
        startActivity(new Intent(this, ActivityLoggerActivity.class));
    }

    public void openCarpooling(View view) {
        startActivity(new Intent(this, CarpoolingActivity.class));
    }

    public void openCarbonFootprint(View view) {
        startActivity(new Intent(this, CarbonFootprintActivity.class));
    }

    public void openChallenges(View view) {
        startActivity(new Intent(this, ChallengesActivity.class));
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SustainabilityTipsChannel";
            String description = "Channel for daily sustainability tips";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("TIP_CHANNEL", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d("NotificationChannel", "Channel created successfully");
        }
    }

    private void setupPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Log.d("PermissionCheck", "Notification permission granted");
                        scheduleDailyNotification();
                        Toast.makeText(this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("PermissionCheck", "Notification permission denied");
                        Toast.makeText(this, "Permission Denied. Enable notifications in settings.", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("PermissionCheck", "Permission already granted, scheduling notifications.");
                scheduleDailyNotification();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Toast.makeText(this, "This app needs notification permissions to send daily tips.", Toast.LENGTH_LONG).show();
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            scheduleDailyNotification();
        }
    }

    private void scheduleDailyNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        alarmManager.cancel(pendingIntent);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );

        Log.d("NotificationSchedule", "Daily notification set for 9 AM");
    }
}
