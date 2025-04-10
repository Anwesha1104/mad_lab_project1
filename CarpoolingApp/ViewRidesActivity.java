package com.example.lab_project;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ViewRidesActivity extends AppCompatActivity {

    ListView rideListView;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rides);

        rideListView = findViewById(R.id.rideListView);
        dbHelper = new UserDatabaseHelper(this);

        List<String> rides = dbHelper.getAllRides();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rides);
        rideListView.setAdapter(adapter);
    }
}
