package com.example.chordconnectapp.fragments;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chordconnectapp.LoginPage;
import com.example.chordconnectapp.MainActivity;
import com.example.chordconnectapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ProfileFragment extends Fragment {
    TextView userProfileName;
    TextView userAddress;
    TextView userPhone;
    TextView userEmail;
    ImageView userImage;
    FloatingActionButton logoutButton;

    ParseUser currentUser;

    List<ParseUser> nearMusicians;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // extract views
        userProfileName = view.findViewById(R.id.userProfileName);
        userAddress = view.findViewById(R.id.userAddress);
        userPhone = view.findViewById(R.id.userPhone);
        userEmail = view.findViewById(R.id.userEmail);
        logoutButton = view.findViewById(R.id.logoutButton);
        userImage = view.findViewById(R.id.ivUserImage);


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

        ParseFile profileImage = currentUser.getParseFile("profile_picture");
        Log.d("ProfileFragement", String.valueOf(profileImage));

        userProfileName.setText(currentUser.getUsername());
        userEmail.setText(currentUser.getEmail());
        userAddress.setText(address);
        userPhone.setText(phone);

        try{
            Glide.with(this).load(profileImage.getUrl()).into(userImage);
        }
        catch (Exception e){
            e.getStackTrace();
        }



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

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        String address = addresses.get(0).getAddressLine(0);

        return address;

    }

    private void goLoginPage() {

        Intent loginIntent = new Intent(getContext(), LoginPage.class);
        startActivity(loginIntent);
        getActivity().finish();

    }
}