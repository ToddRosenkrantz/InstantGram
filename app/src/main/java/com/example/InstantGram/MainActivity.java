package com.example.InstantGram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private ImageButton ibHome;
    private ImageButton ibPost;
    private ImageButton ibProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton ibHome = findViewById(R.id.ibHome);
        final ImageButton ibPost = findViewById(R.id.ibPost);
        final ImageButton ibProfile = findViewById(R.id.ibProfile);

        queryPosts();

        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
                Log.i(TAG, "onClick Home button");
                ibHome.setImageResource(R.drawable.instagram_home_filled_24);
                ibPost.setImageResource(R.drawable.instagram_new_post_outline_24);
                ibProfile.setImageResource(R.drawable.instagram_user_outline_24);
//                loginViewModel.login(etUserName.getText().toString(),
//                        etPassword.getText().toString());
//                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        ibPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
                Log.i(TAG, "onClick Post button");
                ibHome.setImageResource(R.drawable.instagram_home_outline_24);
                ibPost.setImageResource(R.drawable.instagram_new_post_filled_24);
                ibProfile.setImageResource(R.drawable.instagram_user_outline_24);
//                loginViewModel.login(etUserName.getText().toString(),
//                        etPassword.getText().toString());
//                loadingProgressBar.setVisibility(View.INVISIBLE);
                goPostActivity();
            }
        });
        ibProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
                Log.i(TAG, "onClick Profile button");
                ibHome.setImageResource(R.drawable.instagram_home_outline_24);
                ibPost.setImageResource(R.drawable.instagram_new_post_outline_24);
                ibProfile.setImageResource(R.drawable.instagram_user_filled_24);
//                loginViewModel.login(etUserName.getText().toString(),
//                        etPassword.getText().toString());
//                loadingProgressBar.setVisibility(View.INVISIBLE);
                goProfile();
            }
        });


    }
    private void queryPosts(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include((Post.KEY_USER));
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG,"Errory in query " + e.getMessage(), e);
                    return;
                }
                for(Post post : posts){
                        Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

            }
        });
    }
    private void goPostActivity() {
        Intent i = new Intent(this, PostActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        ibProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // change images, go to activity/fragment
                ibHome.setImageResource(R.drawable.instagram_home_outline_24);
                ibProfile.setImageResource(R.drawable.instagram_user_filled_24);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miLogout:
                goProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void goProfile() {
        Intent i = new Intent(this, LogoutActivity.class);
        startActivity(i);
    }
}