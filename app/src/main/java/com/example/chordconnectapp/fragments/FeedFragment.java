package com.example.chordconnectapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chordconnectapp.FeedAdapter;
import com.example.chordconnectapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    RecyclerView rvNearMusicians;
    List<ParseUser> nearMusicians;


    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        rvNearMusicians = view.findViewById(R.id.rvNearMusicians);

        ParseUser currentUser = ParseUser.getCurrentUser();
        nearMusicians = new ArrayList<>();

        FeedAdapter adapter = new FeedAdapter(getContext(), nearMusicians, currentUser);
        rvNearMusicians.setAdapter(adapter);
        rvNearMusicians.setLayoutManager(new LinearLayoutManager(getContext()));

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNear("location", currentUser.getParseGeoPoint("location"));

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override  public void done(List<ParseUser> nearUsers, ParseException e) {
                if (e == null) {
                    // do something with the list of results of your query
                    for(int i = 0; i < nearUsers.size(); i++){
                        if(!nearUsers.get(i).getObjectId().equals(currentUser.getObjectId())) {
                            nearMusicians.add(nearUsers.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    Log.d("MainActivity", String.valueOf(nearMusicians.size()));
                } else {
                    // handle the error
                    Log.e("MainActivity", e.getMessage());
                }
            }
        });

        ParseQuery.clearAllCachedResults();



    }
}