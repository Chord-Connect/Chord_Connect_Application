package com.example.chordconnectapp;

import android.app.Application;

import com.parse.Parse;

public class ParseBackend extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JDFC6ye598bp8w4EUNjA4dEUbutkr3gZAD4wFGOV")
                .clientKey("tiPXCZUhhx1nNQ09fYsY48CddhlYxXuVAZ7gdxGQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
