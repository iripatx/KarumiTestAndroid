package com.example.karumicodingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogoutActivity extends AppCompatActivity {

    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        // Creating the session manager
        manager = new SessionManager(this);

        // Formatting text with the logged e-mail
        TextView loggedText = findViewById(R.id.loggedTextView);
        String welcome_text = " Welcome!\n You are logged in the app as " + manager.getMail() +
                ".\n You can log out anytime. \n\n Thank you for using the app :D";
        loggedText.setText(welcome_text);

        // Setting the log in button listener
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        // Delete session data and return to the login screen
        manager.deleteSession();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}