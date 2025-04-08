package com.example.lab_project;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityLoggerActivity extends AppCompatActivity {
    private EditText editActivityName, editDate;
    private Button btnLogActivity;
    private RecyclerView recyclerView;
    private ActivityLogAdapter adapter;
    private List<ActivityLog> activityLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);

        // Initialize UI components
        editActivityName = findViewById(R.id.editActivityName);
        editDate = findViewById(R.id.editDate);
        btnLogActivity = findViewById(R.id.btnLogActivity);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize activity log list
        activityLogs = new ArrayList<>();
        adapter = new ActivityLogAdapter(activityLogs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Date Picker for Date Input
        editDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ActivityLoggerActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editDate.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Button Click to Log Activity
        btnLogActivity.setOnClickListener(v -> {
            String activityName = editActivityName.getText().toString().trim();
            String date = editDate.getText().toString().trim();

            if (activityName.isEmpty() || date.isEmpty()) {
                Toast.makeText(ActivityLoggerActivity.this, "Please enter activity and date", Toast.LENGTH_SHORT).show();
                return;
            }

            activityLogs.add(new ActivityLog(activityName, date));
            adapter.notifyDataSetChanged();

            // Clear input fields
            editActivityName.setText("");
            editDate.setText("");

            Toast.makeText(ActivityLoggerActivity.this, "Activity Logged!", Toast.LENGTH_SHORT).show();
        });
    }
}
