package com.example.lab_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

public class RideAdapter extends ArrayAdapter<Ride> {

    public RideAdapter(Context context, List<Ride> rides) {
        super(context, 0, rides);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Ride ride = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ride_item, parent, false);
        }

        TextView rideInfo = convertView.findViewById(R.id.rideInfo);
        Button requestButton = convertView.findViewById(R.id.requestButton);

        rideInfo.setText(ride.toString());

        requestButton.setOnClickListener(v -> {
            String currentUser = "user123"; // Replace with actual logged-in user ID
            String creator = ride.getCreator();
            String rideId = String.valueOf(ride.getId()); // Or just ride.getId() if it's already a String

            UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());
            boolean success = dbHelper.saveRideRequest(rideId, currentUser, creator);

            if (success) {
                Toast.makeText(getContext(), "Request stored in database", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to store request", Toast.LENGTH_SHORT).show();
            }
        });

        Button callButton = convertView.findViewById(R.id.callButton);
        callButton.setOnClickListener(v -> {
            String phone = ride.getPhoneNumber(); // get phone number from Ride object

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            getContext().startActivity(intent);

        });

        Button chatButton = convertView.findViewById(R.id.chatButton);
        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("rideId", String.valueOf(ride.getId()));
            intent.putExtra("currentUser", "user123"); // Replace with actual user if available
            getContext().startActivity(intent);
        });


        return convertView;
    }
}
