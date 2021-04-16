package com.example.chordconnectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpPage extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    private static final int REQUEST_CODE = 0010;
    ParseGeoPoint userLocation;

    TextView signUpUsername;
    TextView signUpPassword;
    TextView signUpPassword2;
    TextView signUpEmail;
    TextView signUpPhone;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        signUpUsername = findViewById(R.id.signUpUsername);
        signUpPassword = findViewById(R.id.signUpPassword);
        signUpPassword2 = findViewById(R.id.signUpPassword2);
        registerButton = findViewById(R.id.signUpButton);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPhone = findViewById(R.id.signUpPhone);

        if(ActivityCompat.checkSelfPermission(SignUpPage.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SignUpPage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        getLocation();

        if(userLocation != null){
            Toast.makeText(this, "Location Acquired", Toast.LENGTH_SHORT).show();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = signUpUsername.getText().toString();
                String password = signUpPassword.getText().toString();
                String password2 = signUpPassword2.getText().toString();
                String email = signUpEmail.getText().toString();
                String phone = signUpPhone.getText().toString();

                registerUser(username, password, password2, email, phone);
            }
        });


    }

    private void registerUser(String username, String password, String password2, String email, String phone) {

        ParseUser newUser = new ParseUser();


        if(password.equals(password2)){

            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.put("location", userLocation);
            newUser.setEmail(email);
            newUser.put("phone", phone);

            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        goMainActivity();
                        Toast.makeText(SignUpPage.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignUpPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else{
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainActivity() {

        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 50, SignUpPage.this);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try{
            userLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}