package com.example.karumicodingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginActivity extends AppCompatActivity {

    // Attributes
    private EditText emailText;
    private EditText passText;
    private Button loginButton;
    private SessionManager manager;
    private RequestQueue mQueue;

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
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkCredentials();
            }
        });

        // Creating request queue for the mock API
        mQueue = Volley.newRequestQueue(this);

    }

    private void login(String email){
        // Creating and storing session
        manager.createSession(email);
        Intent intent = new Intent(this, LogoutActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkCredentials(){
        // Getting inputs
        final String email = emailText.getText().toString();
        final String pass = passText.getText().toString();

        // API URL
        String url = "https://run.mocky.io/v3/f8bfa68c-1f87-4c3b-ad94-59956dcf07c7";

        // Creating request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>(){
                @Override

                // onResponse will be called if the app succesfully connects to the API and receives the JSON
                public void onResponse(JSONObject response){
                    try {
                        // Getting the user info table
                        JSONArray jsonArray = response.getJSONArray("User Info");
                        // Only the first (and only) element is required
                        JSONObject userinfo = jsonArray.getJSONObject(0);
                        // Retrieving credentials
                        String APIemail = userinfo.getString("email");
                        String APIpass = userinfo.getString("pass");

                        // Checking credentials
                        if (APIemail.equals(email) && APIpass.equals(pass)){
                            // If credentials are true, start the login process
                            login(email);
                        }else{
                            //If not, prompt a message and delete pass field
                            passText.setText("");
                            Toast.makeText(LoginActivity.this, "Incorrect mail or password", Toast.LENGTH_SHORT).show();
                            // Unlocking the button to allow more attempts
                            loginButton.setEnabled(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Unlocking the button to allow more attempts
                        loginButton.setEnabled(true);
                    }
                }
            }, new Response.ErrorListener(){
            @Override

            // onErrorResponse
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
                Toast.makeText(LoginActivity.this, "API error", Toast.LENGTH_SHORT).show();
                // Unlocking the button to allow more attempts
                loginButton.setEnabled(true);
            }
        });

        // Sending request
        mQueue.add(request);

        // Blocking button to avoid multiple requests
        loginButton.setEnabled(false);
    }
}