package com.example.lab_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameField, emailField, passwordField;
    EditText ageField, locationField, phoneField;
    Button registerButton;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameField = findViewById(R.id.usernameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        ageField = findViewById(R.id.ageField);
        locationField = findViewById(R.id.locationField);
        phoneField = findViewById(R.id.phoneField);
        registerButton = findViewById(R.id.registerButton);

        dbHelper = new UserDatabaseHelper(this);

        registerButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String age = ageField.getText().toString().trim();
            String location = locationField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    age.isEmpty() || location.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.registerUser(username, email, password, age, location, phone);

            if (success) {
                Toast.makeText(this, "User registered!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to main page
            } else {
                Toast.makeText(this, "Registration failed. Email might already exist.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
