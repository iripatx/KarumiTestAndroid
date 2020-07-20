package com.example.karumicodingtest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/* THIS CLASS STORES SESSION INFORMATION LOCALLY */
public class SessionManager extends Application {
    // Declaring a SharedPreference object and its editor
    SharedPreferences spref;
    SharedPreferences.Editor editor;

    // Getting the context
    Context ctx;

    // SHARED PREFERENCE CONFIGURATION
    // File name
    private static final String PREF_NAME = "KarumiLoginInfo";

    // KEYS
    // Login status
    public static final String KEY_LOGIN_STATUS = "isLoggedIn";
    // Email address
    public static final String KEY_EMAIL = "email";

    // Constructor
    public SessionManager(Context context){
        this.ctx = context;
        // Creating the shared preference in PRIVATE_MODE, so only this app can access its content
        spref = ctx.getSharedPreferences(PREF_NAME, 0);
        editor = spref.edit();
    }

    public void createSession(String email){
        // Setting login status to true
        editor.putBoolean(KEY_LOGIN_STATUS, true);
        // Storing associated mail
        editor.putString(KEY_EMAIL, email);
        // Commit changes
        editor.commit();
    }

    public void deleteSession(){
        // Clearing all data and commiting changes
        editor.clear();
        editor.commit();
    }

    public boolean checkLoginStatus(){
        return spref.getBoolean(KEY_LOGIN_STATUS, false);
    }
}
