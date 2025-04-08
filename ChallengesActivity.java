// ChallengesActivity.java
package com.example.lab_project;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChallengesActivity extends AppCompatActivity {

    RecyclerView recyclerChallenges;
    List<Challenge> challengeList;
    List<Challenge> dashboardList = new ArrayList<>(); // Stores confirmed challenges

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        recyclerChallenges = findViewById(R.id.recyclerChallenges);
        recyclerChallenges.setLayoutManager(new LinearLayoutManager(this));

        challengeList = new ArrayList<>();
        challengeList.add(new Challenge("Save Water", "Turn off taps while brushing and use low-flow fixtures.", R.drawable.save_water));
        challengeList.add(new Challenge("Reduce Plastic", "Use cloth bags and avoid single-use plastic bottles and straws.", R.drawable.reduce_plastic));
        challengeList.add(new Challenge("Use Public Transport", "Reduce carbon footprint by taking buses, trains, or carpooling.", R.drawable.public_transport));
        challengeList.add(new Challenge("Switch to LED Bulbs", "LEDs consume less power and last longer than incandescent bulbs.", R.drawable.led_bulbs));
        challengeList.add(new Challenge("Recycle Waste", "Separate recyclables like paper, glass, and plastic for proper disposal.", R.drawable.recycle));
        challengeList.add(new Challenge("Eat Local & Seasonal", "Support local farmers and reduce emissions from transported goods.", R.drawable.local_food));
        challengeList.add(new Challenge("Plant a Tree", "Trees absorb carbon dioxide and improve air quality.", R.drawable.plant_tree));
        challengeList.add(new Challenge("Go Paperless", "Opt for digital receipts, bills, and reduce paper printing.", R.drawable.paperless));
        challengeList.add(new Challenge("Use Solar Energy", "Install solar panels or solar lights to use renewable energy.", R.drawable.solar_energy));
        challengeList.add(new Challenge("Compost Organic Waste", "Turn kitchen scraps into nutrient-rich soil for gardening.", R.drawable.compost));

        ChallengeAdapter adapter = new ChallengeAdapter(this, challengeList, challenge -> {
            DashboardManager.addChallenge(challenge);
            Toast.makeText(this, challenge.getTitle() + " added to Dashboard!", Toast.LENGTH_SHORT).show();

        });

        recyclerChallenges.setAdapter(adapter);
    }
}

