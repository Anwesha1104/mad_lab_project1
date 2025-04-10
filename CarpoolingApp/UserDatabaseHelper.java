package com.example.lab_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;


public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ecopool_users.db";
    private static final int DB_VERSION = 3;

    public UserDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB_CREATE", "Creating tables...");

        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "age TEXT, " +
                "location TEXT, " +
                "phone TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS rides (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "pickup TEXT, " +
                "dropoff TEXT, " +
                "timestamp TEXT, " +
                "vacancies INTEGER, " +
                "phone_number TEXT, " +
                "creator TEXT)");


        db.execSQL("CREATE TABLE IF NOT EXISTS ride_requests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ride_id INTEGER, " +
                "requester_id TEXT, " +
                "ride_creator_id TEXT, " +
                "status TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS chat_messages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ride_id TEXT, " +
                "sender TEXT, " +
                "message TEXT)");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS rides"); // 👈 add this
        db.execSQL("ALTER TABLE rides ADD COLUMN vacancies INTEGER DEFAULT 0");
        db.execSQL("CREATE TABLE IF NOT EXISTS chat_messages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ride_id TEXT, " +
                "sender TEXT, " +
                "message TEXT, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
        onCreate(db);
    }



    // ✅ This method must be OUTSIDE any other method
    public boolean registerUser(String username, String email, String password, String age, String location, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("age", age);
        values.put("location", location);
        values.put("phone", phone);

        long result = db.insert("users", null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    public boolean saveRide(String pickup, String dropoff, String timestamp, int vacancies, String creator) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pickup", pickup);
        values.put("dropoff", dropoff);
        values.put("timestamp", timestamp);
        values.put("vacancies", vacancies);
        values.put("phone_number", creator);

        long result = db.insert("rides", null, values);
        if (result == -1) {
            Log.e("DB_INSERT", "Failed to insert ride");
        } else {
            Log.d("DB_INSERT", "Ride inserted with ID: " + result);
        }

        db.close();
        return result != -1;

    }

    public List<String> getAllRides() {
        List<String> rideList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM rides ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String pickup = cursor.getString(cursor.getColumnIndexOrThrow("pickup"));
                String dropoff = cursor.getString(cursor.getColumnIndexOrThrow("dropoff"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                rideList.add("From: " + pickup + "\nTo: " + dropoff + "\nAt: " + timestamp);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return rideList;
    }

    public List<Ride> getFilteredRides(String pickup, String dropoff) {
        List<Ride> rides = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "rides",
                null,
                "pickup = ? AND dropoff = ?",
                new String[]{pickup, dropoff},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String p = cursor.getString(cursor.getColumnIndexOrThrow("pickup"));
                String d = cursor.getString(cursor.getColumnIndexOrThrow("dropoff"));
                String ts = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                int v = cursor.getInt(cursor.getColumnIndexOrThrow("vacancies"));
                rides.add(new Ride(id, p, d, ts, v));
            } while (cursor.moveToNext());
        }

        cursor.close();


        return rides;
    }
    public boolean saveRideRequest(String rideId, String requesterId, String rideCreatorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ride_id", rideId);
        values.put("requester_id", requesterId);
        values.put("ride_creator_id", rideCreatorId);
        values.put("status", "pending"); // Default status

        long result = db.insert("ride_requests", null, values);
        db.close();
        return result != -1;
    }
    public void saveChatMessage(String rideId, String sender, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ride_id", rideId);
        values.put("sender", sender);
        values.put("message", message);
        db.insert("chat_messages", null, values);
    }



    public List<String> getChatMessages(String rideId) {
        List<String> messages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM chat_messages WHERE ride_id = ? ORDER BY timestamp ASC", new String[]{rideId});

        while (cursor.moveToNext()) {
            String sender = cursor.getString(cursor.getColumnIndex("sender_id"));
            String msg = cursor.getString(cursor.getColumnIndex("message"));
            messages.add(sender + ": " + msg);
        }

        cursor.close();
        return messages;
    }

    public List<ChatMessage> getChatMessagesAsObjects(String rideId) {
        List<ChatMessage> messages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sender, message FROM chat_messages WHERE ride_id = ?", new String[]{rideId});
        while (cursor.moveToNext()) {
            String sender = cursor.getString(0);
            String message = cursor.getString(1);
            messages.add(new ChatMessage(sender, message));
        }
        cursor.close();
        return messages;
    }

}
