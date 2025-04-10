package com.example.lab_project;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class FilteredRidesActivity extends AppCompatActivity {
    ListView rideListView;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rides);

        rideListView = findViewById(R.id.rideListView);
        dbHelper = new UserDatabaseHelper(this);

        String pickup = getIntent().getStringExtra("pickup");
        String dropoff = getIntent().getStringExtra("dropoff");

        List<Ride> rides = dbHelper.getFilteredRides(pickup, dropoff);
        RideAdapter adapter = new RideAdapter(this, rides);
        rideListView.setAdapter(adapter);
    }
}
