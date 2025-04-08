package com.example.lab_project;

import java.util.ArrayList;
import java.util.List;

public class DashboardManager {
    private static final List<Challenge> dashboardList = new ArrayList<>();

    public static void addChallenge(Challenge challenge) {
        dashboardList.add(challenge);
    }

    public static List<Challenge> getDashboardList() {
        return dashboardList;
    }
}
