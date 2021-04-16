package com.example.chordconnectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TextView userProfileName;
    TextView userAddress;
    TextView userPhone;
    TextView userEmail;
    Button logoutButton;

    ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // extract views
        userProfileName = findViewById(R.id.userProfileName);
        userAddress = findViewById(R.id.userAddress);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        logoutButton = findViewById(R.id.logoutButton);

        // get current user
        currentUser = ParseUser.getCurrentUser();

        // get user information from the database and display it
        String phone = (String) currentUser.get("phone");
        String address = null;
        try {
            address = getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userProfileName.setText(currentUser.getUsername());
        userEmail.setText(currentUser.getEmail());
        userAddress.setText(address);
        userPhone.setText(phone);





        // implement log out functionality
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                goLoginPage();
            }
        });



    }

    private String getAddress() throws IOException {

        // get geopoint
        ParseGeoPoint location = currentUser.getParseGeoPoint("location");

        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        String address = addresses.get(0).getAddressLine(0);

        return address;

    }

    private void goLoginPage() {

        Intent loginIntent = new Intent(this, LoginPage.class);
        startActivity(loginIntent);
        finish();

    }
}