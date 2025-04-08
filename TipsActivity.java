package com.example.lab_project;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TipsAdapter tipsAdapter;
    private List<Tip> tipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        recyclerView = findViewById(R.id.recyclerViewTips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample sustainability tips
        tipList = new ArrayList<>();
        tipList.add(new Tip("Save Water", "Turn off taps while brushing and use low-flow fixtures.", R.drawable.water_saving));
        tipList.add(new Tip("Reduce Plastic Use", "Carry reusable bags and bottles instead of plastic.", R.drawable.plastic_free));
        tipList.add(new Tip("Energy Efficiency", "Use LED bulbs and unplug devices when not in use.", R.drawable.energy_saving));
        tipList.add(new Tip("Eco-Friendly Transport", "Use public transport, cycle, or carpool.", R.drawable.transport));
        tipList.add(new Tip("Sustainable Eating", "Buy local, organic food and reduce food waste.", R.drawable.sustainable_food));
        tipList.add(new Tip("Compost Organic Waste", "Turn food scraps into compost to enrich soil and reduce landfill waste.", R.drawable.compost));
        tipList.add(new Tip("Use Renewable Energy", "Install solar panels or opt for green energy providers.", R.drawable.solar_energy));
        tipList.add(new Tip("Smart Thermostat Usage", "Adjust thermostat settings to save energy and reduce carbon footprint.", R.drawable.thermostat));
        tipList.add(new Tip("Reduce Paper Waste", "Switch to digital notes, e-bills, and reusable napkins.", R.drawable.paperless));
        tipList.add(new Tip("Conserve Water Outdoors", "Water plants in the morning/evening and use rainwater harvesting.", R.drawable.rainwater));
        tipList.add(new Tip("Recycle Electronics", "Properly dispose of e-waste to prevent toxic pollution.", R.drawable.ewaste));
        tipList.add(new Tip("Support Green Brands", "Choose companies that follow ethical and eco-friendly practices.", R.drawable.green_brands));
        tipList.add(new Tip("Reduce Air Travel", "Opt for trains or buses over flights to cut carbon emissions.", R.drawable.low_travel_emission));
        tipsAdapter = new TipsAdapter(this, tipList);
        recyclerView.setAdapter(tipsAdapter);
    }
}
