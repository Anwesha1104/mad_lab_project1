package com.example.lab_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CarpoolingActivity extends AppCompatActivity {

    Button registerBtn;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpooling);

        registerBtn = findViewById(R.id.registerBtn);
        loginButton = findViewById(R.id.loginButton);
        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CarpoolingActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(CarpoolingActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
