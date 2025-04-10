package com.example.lab_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.view.View;
public class CreateRideActivity extends AppCompatActivity {

    EditText pickupEditText, dropoffEditText, dateEditText, timeEditText;
    Button saveRideButton;
    Spinner vacanciesSpinner;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);


        pickupEditText = findViewById(R.id.pickupEditText);
        dropoffEditText = findViewById(R.id.dropoffEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        saveRideButton = findViewById(R.id.saveRideButton);
        vacanciesSpinner = findViewById(R.id.vacanciesSpinner);
        dbHelper = new UserDatabaseHelper(this);
        ImageView mockMap = findViewById(R.id.mockMap);


        mockMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRideActivity.this, OsmMapActivity.class);
                startActivity(intent);
            }
        });

        // Spinner setup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.vacancy_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacanciesSpinner.setAdapter(adapter);

        // Date Picker
        Calendar calendar = Calendar.getInstance();
        dateEditText.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(this, (view, y, m, d) -> {
                dateEditText.setText(d + "/" + (m + 1) + "/" + y);
            }, year, month, day);
            datePicker.show();
        });

        // Time Picker
        timeEditText.setOnClickListener(v -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(this, (view, h, m) -> {
                timeEditText.setText(String.format("%02d:%02d", h, m));
            }, hour, minute, true);
            timePicker.show();
        });

        // Save button listener
        saveRideButton.setOnClickListener(v -> {
            String pickup = pickupEditText.getText().toString().trim();
            String dropoff = dropoffEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String time = timeEditText.getText().toString().trim();

            if (pickup.isEmpty() || dropoff.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int vacancies = Integer.parseInt(vacanciesSpinner.getSelectedItem().toString());
            String timestamp = date + " " + time;

            String creatorUsername = getIntent().getStringExtra("username"); // or "loggedInUser" etc.

            boolean result = dbHelper.saveRide(pickup, dropoff, timestamp, vacancies, creatorUsername);

            if (result) {
                Toast.makeText(this, "Ride created!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to create ride", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
