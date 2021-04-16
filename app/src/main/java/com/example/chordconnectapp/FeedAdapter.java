package com.example.chordconnectapp;

import android.content.Context;
import android.icu.number.Precision;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chordconnectapp.fragments.FeedFragment;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.util.List;

public class FeedAdapter extends  RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    Context context;
    List<ParseUser> nearMusicians;
    ParseUser currentUser;

    public FeedAdapter(Context context, List<ParseUser> nearMusicians, ParseUser currentUser) {

        this.context = context;
        this.nearMusicians = nearMusicians;
        this.currentUser = currentUser;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View musicianView = LayoutInflater.from(context).inflate(R.layout.list_near_musicians, parent, false);
        return new ViewHolder(musicianView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ParseUser musician = nearMusicians.get(position);
        holder.bind(musician);

    }

    @Override
    public int getItemCount() {
        return nearMusicians.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivMusicianImage;
        TextView tvMusicianUsername;
        TextView tvMusicianDistance;
        TextView tvMusicianContact;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvMusicianUsername = itemView.findViewById(R.id.tvMusicianUsername);
            ivMusicianImage = itemView.findViewById(R.id.ivMusicianImage);
            tvMusicianDistance = itemView.findViewById(R.id.tvMusicianDistance);
            tvMusicianContact = itemView.findViewById(R.id.tvMusicianContact);

        }

        public void bind(ParseUser musician) {

            try{
                tvMusicianUsername.setText(musician.getUsername());
                tvMusicianContact.setText((String)musician.get("phone"));
                double d = Math.round(currentUser.getParseGeoPoint("location").distanceInMilesTo(musician.getParseGeoPoint("location")));
                String distance = String.valueOf(d) + " mi";
                tvMusicianDistance.setText(distance);
                Log.d("FeedAdapter", "Successfully binded");
            }catch (Exception e){
                Log.e("FeedAdapter", "Error in binding");
            }

        }
    }
}
