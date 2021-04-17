package com.example.chordconnectapp.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chordconnectapp.R;
import com.example.chordconnectapp.models.AudioPost;

import java.io.IOException;
import java.util.List;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {
    Context context;
    List<AudioPost> posts;

    public ProfilePostAdapter(Context context, List<AudioPost> audioPosts){
        this.context = context;
        this.posts = audioPosts;
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }
    // Add a list of items -- change to type used
    public void addAll(List<AudioPost> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_profile, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AudioPost post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvAudioId;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvLength;
        TextView tvDate;
        ImageButton ibPlay;

        public ViewHolder(View view) {
            super(view);
            tvAudioId = view.findViewById(R.id.tvAudioId);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDescription = view.findViewById(R.id.tvDescription);
            tvLength = view.findViewById(R.id.tvLength);
            tvDate = view.findViewById(R.id.tvDate);
            ibPlay = view.findViewById(R.id.ibPlayAudio);
        }

        private void bind(AudioPost post) {
            tvTitle.setText(post.getTitle());
            tvDescription.setText(post.getDescription());
            tvDate.setText(post.getPostCreationDate());

            ibPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = post.getAudioURL();
                    final MediaPlayer mediaPlayer = new MediaPlayer();
                    // Set type to streaming
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    // Listen for if the audio file can't be prepared
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            // ... react appropriately ...
                            // The MediaPlayer has moved to the Error state, must be reset!
                            return false;
                        }
                    });
                    // Attach to when audio file is prepared for playing
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                    // Set the data source to the remote URL
                    try {
                        mediaPlayer.setDataSource(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Trigger an async preparation which will file listener when completed
                    mediaPlayer.prepareAsync();

                }
            });

        }
    }
}
