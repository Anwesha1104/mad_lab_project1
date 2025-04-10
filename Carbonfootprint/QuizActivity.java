package com.example.lab_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5;
    private Button btnSubmit;
    private TextView textFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize UI elements
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup5 = findViewById(R.id.radioGroup5);
        btnSubmit = findViewById(R.id.btnSubmit);
        textFeedback = findViewById(R.id.textFeedback);

        // Submit button click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });
    }

    private void checkAnswers() {
        int score = 0;

        // Question 1 - Energy Source
        int selectedId1 = radioGroup1.getCheckedRadioButtonId();
        RadioButton selectedRadioButton1 = findViewById(selectedId1);
        if (selectedRadioButton1 != null && selectedRadioButton1.getText().toString().equals("Solar")) {
            score++;
            showToast("Correct! Solar energy is a low carbon footprint energy source.");
        } else {
            showToast("Wrong! Solar energy has the lowest carbon footprint.");
        }

        // Question 2 - Transportation
        int selectedId2 = radioGroup2.getCheckedRadioButtonId();
        RadioButton selectedRadioButton2 = findViewById(selectedId2);
        if (selectedRadioButton2 != null && selectedRadioButton2.getText().toString().equals("Train")) {
            score++;
            showToast("Correct! Trains have the lowest CO₂ emissions per passenger km.");
        } else {
            showToast("Wrong! Trains are one of the most efficient forms of transportation.");
        }

        // Question 3 - Diet
        int selectedId3 = radioGroup3.getCheckedRadioButtonId();
        RadioButton selectedRadioButton3 = findViewById(selectedId3);
        if (selectedRadioButton3 != null && selectedRadioButton3.getText().toString().equals("Vegan")) {
            score++;
            showToast("Correct! A vegan diet has a lower carbon footprint.");
        } else {
            showToast("Wrong! A vegan diet is the most eco-friendly.");
        }

        // Question 4 - Sustainable Living
        int selectedId4 = radioGroup4.getCheckedRadioButtonId();
        RadioButton selectedRadioButton4 = findViewById(selectedId4);
        if (selectedRadioButton4 != null && selectedRadioButton4.getText().toString().equals("Using heat pump")) {
            score++;
            showToast("Correct! Heat pumps are energy-efficient for heating.");
        } else {
            showToast("Wrong! Heat pumps are the best choice for heating with low emissions.");
        }

        // Question 5 - Consumer Habits
        int selectedId5 = radioGroup5.getCheckedRadioButtonId();
        RadioButton selectedRadioButton5 = findViewById(selectedId5);
        if (selectedRadioButton5 != null && selectedRadioButton5.getText().toString().equals("Composting food waste")) {
            score++;
            showToast("Correct! Composting food waste helps reduce emissions.");
        } else {
            showToast("Wrong! Composting is a great way to reduce emissions from waste.");
        }

        // Display final score
        textFeedback.setText("Your score: " + score + " out of 5");
    }

    private void showToast(String message) {
        Toast.makeText(QuizActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}