package com.example.InstantGram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.InstantGram.fragments.ComposeFragment;
import com.example.InstantGram.fragments.PostsFragment;
import com.example.InstantGram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);


// define your fragments here
        final Fragment postsFragment = new PostsFragment();
        final Fragment composeFragment = new ComposeFragment();
        final Fragment profileFragment = new ProfileFragment();
        fragmentManager.beginTransaction().replace(R.id.flContainer, new PostsFragment()).commit();
        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setIcon(R.drawable.instagram_home_outline_24);
                        bottomNavigationView.getMenu().findItem(R.id.action_compose).setIcon(R.drawable.instagram_new_post_outline_24);
                        bottomNavigationView.getMenu().findItem(R.id.action_profile).setIcon(R.drawable.instagram_user_outline_24);
                        switch (item.getItemId()) {
                            case R.id.action_compose:
                                fragment = composeFragment;
                                item.setIcon(R.drawable.instagram_new_post_filled_24);
//                                Toast.makeText(MainActivity.this,"Selected Compose",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.action_profile:
                                item.setIcon(R.drawable.instagram_user_filled_24);
                                fragment = profileFragment;
//                                Toast.makeText(MainActivity.this,"Selected Profile",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.action_home:
                            default:
                                item.setIcon(R.drawable.instagram_home_filled_24);
                                fragment = postsFragment;
//                                Toast.makeText(MainActivity.this,"Selected Home",Toast.LENGTH_LONG).show();
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        return true;
                    }
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}