package com.example.InstantGram;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    public static final String PARSE_APP_ID = BuildConfig.PARSE_APP_ID;       // Change this inside apikey.properties
    public static final String PARSE_CLIENT_KEY = BuildConfig.PARSE_CLIENT_KEY; // Change this inside apikey.properties
    @Override
    public void onCreate() {
        super.onCreate();


        // Register your parse models
        ParseObject.registerSubclass(Post.class);
        // set applicationId, and server server based on the values in the back4app settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(PARSE_APP_ID) // should correspond to Application Id env variable
                .clientKey(PARSE_CLIENT_KEY)  // should correspond to Client key env variable
                .server("https://parseapi.back4app.com").build());


    }
}
