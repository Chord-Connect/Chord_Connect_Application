package com.example.chordconnectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginPage extends AppCompatActivity {

    TextView loginUsername;
    TextView loginPassword;
    Button loginButton;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        loginUsername = findViewById(R.id.signUpUsername);
        loginPassword = findViewById(R.id.signUpPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                loginUser(username, password);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUpPage();
            }
        });


    }

    private void loginUser(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    goMainActivity();
                    Toast.makeText(LoginPage.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void goMainActivity() {

        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
        finish();

    }

    private void goSignUpPage(){

        Intent signUpPageIntent = new Intent(this, SignUpPage.class);
        startActivity(signUpPageIntent);
        finish();

    }
}