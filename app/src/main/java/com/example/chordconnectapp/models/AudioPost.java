package com.example.chordconnectapp.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;

@ParseClassName("Post")
public class AudioPost extends ParseObject {
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_AUDIO = "audio_file";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";

    public AudioPost() {}

    public String getDescription() {return getString(KEY_DESCRIPTION);}
    public void setDescription(String description) {put(KEY_DESCRIPTION, description);}

    public String getAudioURL() {return getParseFile(KEY_AUDIO).getUrl();}
    public void setAudio(ParseFile audio) {put(KEY_AUDIO, audio);}

    public ParseUser getUser(){return getParseUser(KEY_USER);}
    public void setUser(ParseUser user){put(KEY_USER, user);}
    public String getTitle() {return getString(KEY_TITLE);}
    public void setTitle(String title){put(KEY_TITLE, title);}
    public String getPostCreationDate(){
        SimpleDateFormat myFormatObj = new SimpleDateFormat("MM/dd/yyyy");
        String date = myFormatObj.format(getCreatedAt());
        return date;}

}
