package com.example.lab_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CarbonFootprintActivity extends AppCompatActivity {

    private EditText inputDistance, inputElectricity;
    private Spinner spinnerVehicle, spinnerElectricity, spinnerDiet;
    private Button btnCalculate, btnQuiz;
    private TextView textResult;
    private LinearLayout resultContainer;
    private double carbonFootprint = 0.0;  // <-- Moved here for global access

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint);

        // Initialize UI elements
        inputDistance = findViewById(R.id.input_distance);
        inputElectricity = findViewById(R.id.input_electricity);
        spinnerVehicle = findViewById(R.id.spinner_vehicle);
        spinnerElectricity = findViewById(R.id.spinner_electricity);
        spinnerDiet = findViewById(R.id.spinner_diet);
        textResult = findViewById(R.id.text_result);
        resultContainer = findViewById(R.id.result_container);
        btnCalculate = findViewById(R.id.btn_calculate);
        btnQuiz = findViewById(R.id.btn_quiz);

        // Calculate button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCarbonFootprint();
            }
        });

        // Quiz button
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarbonFootprintActivity.this, QuizActivity.class);
                intent.putExtra("carbonScore", carbonFootprint);  // Now accessible
                startActivity(intent);
            }
        });

        Button btnCompare = findViewById(R.id.btn_compare);
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarbonFootprintActivity.this, ComparisonActivity.class);
                intent.putExtra("carbonScore", (float) carbonFootprint); // Pass score to graph page
                startActivity(intent);
            }
        });

    }

    private void calculateCarbonFootprint() {
        String distance = inputDistance.getText().toString();
        String electricityUsage = inputElectricity.getText().toString();

        if (distance.isEmpty() || electricityUsage.isEmpty()) {
            Toast.makeText(CarbonFootprintActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate and store in the global variable
        double distanceValue = Double.parseDouble(distance);
        double electricityValue = Double.parseDouble(electricityUsage);
        carbonFootprint = (distanceValue * 0.12) + (electricityValue * 0.6);

        // Show result
        textResult.setText(String.format("Your estimated carbon footprint: %.2f kg CO2/day", carbonFootprint));
        resultContainer.setVisibility(View.VISIBLE);
    }
}

