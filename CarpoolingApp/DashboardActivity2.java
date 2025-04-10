package com.example.lab_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity2 extends AppCompatActivity {

    TextView welcomeMessage;
    Button bookRideButton, viewRidesButton, myBookingsButton, logoutButton, createRideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        String username = getIntent().getStringExtra("username");

        welcomeMessage = findViewById(R.id.welcomeText);
        bookRideButton = findViewById(R.id.bookRideButton);
        viewRidesButton = findViewById(R.id.viewRidesButton);
        myBookingsButton = findViewById(R.id.myBookingsButton);
        logoutButton = findViewById(R.id.logoutButton);

        welcomeMessage.setText("Welcome to EcoPool, " + username + "!");

        bookRideButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookRideActivity.class);
            startActivity(intent);
        });

        viewRidesButton.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity2.this, ViewRidesActivity.class));
        });

        myBookingsButton.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity2.this, OsmMapActivity.class));
        });

        createRideButton = findViewById(R.id.createRideButton);

        createRideButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity2.this, CreateRideActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity2.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
