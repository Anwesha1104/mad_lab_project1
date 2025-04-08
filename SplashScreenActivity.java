package com.example.lab_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the splash screen layout
        setContentView(R.layout.splash_screen);

        // Delay and move to MainActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
            finish();
        }, SPLASH_DURATION);

    }
}
