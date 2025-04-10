package com.example.lab_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText usernameField, passwordField;
    Button loginButton;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.loginUsername);
        passwordField = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        dbHelper = new UserDatabaseHelper(this);

        loginButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValid = dbHelper.checkUserCredentials(username, password);

            if (isValid) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                // Go to MainActivity or Dashboard
                Intent intent = new Intent(LoginActivity.this, DashboardActivity2.class);
                intent.putExtra("username", username); // optional
                startActivity(intent);
                finish();


            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
