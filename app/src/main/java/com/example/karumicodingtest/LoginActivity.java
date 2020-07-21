package com.example.karumicodingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // Attributes
    EditText emailText;
    EditText passText;
    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Before loading the view the session token is checked, in case the user is already logged in
        manager = new SessionManager(this);

        // If already logged in, change to the Logout activity
        if (manager.checkLoginStatus()){
            Intent intent = new Intent(this, LogoutActivity.class);
            startActivity(intent);
            finish();
        }
        // If not, start the login process

        setContentView(R.layout.activity_login);

        // Getting text fields' ids
        emailText = (EditText) findViewById(R.id.editTextEmailAddress);
        passText = (EditText) findViewById(R.id.editTextPassword);

        // Setting the log in button listener
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        // Getting inputs
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();

        // Checking email and password
        // For simplicity sake, they will be checked with hardcoded samples
        // However, in this step the app would communicate with the API to check the credentials
        if(email.equals("test@karumi.com") && pass.equals("potato")){
            // If succesful, create the session and change to the logout activity
            manager.createSession(email);
            Intent intent = new Intent(this, LogoutActivity.class);
            startActivity(intent);
            finish();
        }else{
            //If not, prompt a message and delete pass field
            passText.setText("");
            Toast.makeText(this, "Incorrect mail or password", Toast.LENGTH_SHORT).show();
        }
    }
}