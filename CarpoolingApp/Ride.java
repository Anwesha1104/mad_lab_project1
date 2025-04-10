package com.example.lab_project;

public class Ride {
    public int id;
    public String pickup;
    public String dropoff;
    public String timestamp;
    public int vacancies;
    public String creator;

    private String phoneNumber;
    public Ride(int id, String pickup, String dropoff, String timestamp, int vacancies) {
        this.id = id;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.timestamp = timestamp;
        this.vacancies = vacancies;
        this.creator = creator;
    }
    public int getId() { return id; }
    public String getCreator() { return creator; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return pickup + " → " + dropoff + "\nTime: " + timestamp + "\nVacancies: " + vacancies;
    }
}
