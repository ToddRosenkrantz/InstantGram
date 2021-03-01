package com.example.InstantGram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.InstantGram.ui.login.LoginActivity;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignupActivity";
    private EditText etNewUser;
    private EditText etNewPassword;
    private EditText etNewEmail;
    private EditText etNewPhone;
    private Button btnRegister;
    private Button btnCancelReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etNewUser = findViewById(R.id.etNewUser);
        etNewPassword = findViewById(R.id.etNewPassword);
        etNewEmail= findViewById(R.id.etNewEmail);
        etNewPhone = findViewById(R.id.etNewPhone);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancelReg = findViewById(R.id.btnCancelReg);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Register button click");
                String username = etNewUser.getText().toString();
                String password = etNewPassword.getText().toString();
                registerUser();
            }
        });

    }
    private void registerUser() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", etNewUser.getText().toString()); // find adults
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, com.parse.ParseException e) {
                if (e != null) {
                    // The query was successful.
                    Log.e(TAG,"User already exists");
                } else {
                    // User is new
                    // Create the ParseUser
                    ParseUser user = new ParseUser();
                    // Set core properties
                    user.setUsername(etNewUser.getText().toString());
                    user.setPassword(etNewPassword.getText().toString());
                    //                user.setEmail(etNewEmail.getText().toString());
                    // Set custom properties
                    //                user.put("phone", "650-253-0000");
                    // Invoke signUpInBackground
                    user.signUpInBackground(e1 -> {
                        if (e1 == null) {
                            // Hooray! Let them use the app now.
                            goLoginActivity();

                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            Log.e(TAG,"Error in signup " + e1.getMessage(), e1);
                        }
                    });
                }
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}