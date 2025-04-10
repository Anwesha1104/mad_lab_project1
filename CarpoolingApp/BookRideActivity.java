package com.example.lab_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.Log;
import android.content.Intent;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
public class BookRideActivity extends AppCompatActivity {

    EditText pickupField, dropField;
    Button viewAvailableRidesButton ;

    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);
        dbHelper = new UserDatabaseHelper(this);
        pickupField = findViewById(R.id.pickupField);
        dropField = findViewById(R.id.dropField);
        ImageView mockMap = findViewById(R.id.mockMap);


        mockMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookRideActivity.this, OsmMapActivity.class);
                startActivity(intent);
            }
        });

        viewAvailableRidesButton  = findViewById(R.id.viewAvailableRidesButton );
        Button viewRidesButton = findViewById(R.id.viewRidesButton);
        viewRidesButton.setOnClickListener(v -> {
            startActivity(new Intent(BookRideActivity.this, ViewRidesActivity.class));
        });

        viewAvailableRidesButton.setOnClickListener(v -> {
            String pickup = pickupField.getText().toString().trim();
            String dropoff = dropField.getText().toString().trim();

            if (pickup.isEmpty() || dropoff.isEmpty()) {
                Toast.makeText(this, "Please enter both pickup and dropoff locations.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(BookRideActivity.this, FilteredRidesActivity.class);
                intent.putExtra("pickup", pickup);
                intent.putExtra("dropoff", dropoff);
                startActivity(intent);
            }
        });


    }


}

