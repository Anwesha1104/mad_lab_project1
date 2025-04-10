package com.example.lab_project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import android.widget.TextView;
import java.util.ArrayList;

public class ComparisonActivity extends AppCompatActivity {

    private BarChart barChart;
    private float userFootprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        barChart = findViewById(R.id.barChart);
        TextView percentageDescription = findViewById(R.id.percentageDescription);

        // Get the passed footprint from intent
        userFootprint = getIntent().getFloatExtra("carbonScore", 0f);

        float globalAvg = 4.8f;
        float percentage = (userFootprint / globalAvg) * 100f;

        // Declare and set the description text
        String desc;
        if (userFootprint < globalAvg) {
            desc = String.format("✅ Your carbon footprint is %.1f%% of the global average. Great job!", percentage);
        } else if (userFootprint == globalAvg) {
            desc = "⚖️ Your carbon footprint matches the global average.";
        } else {
            desc = String.format("⚠️ Your carbon footprint is %.1f%% of the global average. Consider reducing it!", percentage);
        }

        // Now set it in the TextView
        percentageDescription.setText(desc);

        showBarChart();
    }

    private void showBarChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, userFootprint)); // User
        entries.add(new BarEntry(1f, 1.9f));           // Avg India
        entries.add(new BarEntry(2f, 4.8f));           // Global avg

        BarDataSet dataSet = new BarDataSet(entries, "Carbon Footprint (kg CO₂/day)");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);

        // Set X-axis labels
        final String[] labels = new String[] {"You", "India Avg", "World Avg"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(12f);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < labels.length) {
                    return labels[index];
                } else {
                    return "";
                }
            }
        });

        barChart.animateY(1000);
        barChart.invalidate(); // Refresh chart



    }
}
