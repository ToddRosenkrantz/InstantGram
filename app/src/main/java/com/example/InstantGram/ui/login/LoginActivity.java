package com.example.InstantGram.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.SigningInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.InstantGram.MainActivity;
import com.example.InstantGram.R;
import com.example.InstantGram.SignupActivity;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 *  A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

//    private Context context = getApplicationContext();
//    private LoginViewModel loginViewModel;

    Boolean found = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }
        setContentView(R.layout.activity_login);
//        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
//                .get(LoginViewModel.class);

        final EditText etUserName = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final Button btnSignup = findViewById(R.id.btnSignup);

//        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

//        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
//            @Override
//            public void onChanged(@Nullable LoginFormState loginFormState) {
//                if (loginFormState == null) {
//                    return;
//                }
//                btnLogin.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null) {
//                    etUserName.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    etPassword.setError(getString(loginFormState.getPasswordError()));
//                }
//            }
//        });

//        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
//            @Override
//            public void onChanged(@Nullable LoginResult loginResult) {
//                if (loginResult == null) {
//                    return;
//                }
//                loadingProgressBar.setVisibility(View.GONE);
//                if (loginResult.getError() != null) {
//                    showLoginFailed(loginResult.getError());
//                }
//                if (loginResult.getSuccess() != null) {
//                    updateUiWithUser(loginResult.getSuccess());
//                }
//                setResult(Activity.RESULT_OK);
//
//                //Complete and destroy login activity once successful
//                finish();
//            }
//        });
//
//        TextWatcher afterTextChangedListener = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // ignore
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // ignore
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                loginViewModel.loginDataChanged(etUserName.getText().toString(),
//                        etPassword.getText().toString());
//            }
//        };
//        etUserName.addTextChangedListener(afterTextChangedListener);
//        etPassword.addTextChangedListener(afterTextChangedListener);
//        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    loginViewModel.login(etUserName.getText().toString(),
//                            etPassword.getText().toString());
//                }
//                return false;
//            }
//        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
                Log.i(TAG, "onClick login button");
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username,password);
//                loginViewModel.login(etUserName.getText().toString(),
//                        etPassword.getText().toString());
//                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignup();
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);
        ParseUser.logInInBackground(username,password , new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                 if (e != null){
                     Log.e(TAG, "Issue with login" + e.getMessage(), e);
                     Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                     // If we got an error, check if username exists
                     // if username exists, give wrong password error
                     if(UserExists(username)){
                         Toast.makeText(LoginActivity.this,"Incorrect password", Toast.LENGTH_SHORT).show();
                     }
                     // if username does not exist popup dialog with offer to register/cancel

                     return;
                 }
                 // TODO : navigagte to Main Activity if login is good
                goMainActivity();
                Toast.makeText(LoginActivity.this,"Success", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Success logging in user " + username);
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
    private void goSignup(){
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

//    private void updateUiWithUser(LoggedInUserView model) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        // TODO : initiate successful logged in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//    }
//
//    private void showLoginFailed(@StringRes Integer errorString) {
//        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
//    }
private boolean UserExists(String username){

    ParseQuery <ParseUser> query = ParseUser.getQuery();
    query.whereEqualTo("username", username); // find adults
    query.findInBackground(new FindCallback<ParseUser>() {
        public void done(List<ParseUser> objects, ParseException e) {
            if (e == null) {
                // The query was successful.
                found = true;
            } else {
                // Something went wrong.
            }
        }
    });
    return found;
    }
}