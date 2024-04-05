package com.example.socialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomBar extends AppCompatActivity {

    private static final String TAG = "BottomBar";

    public void setupBottomBar(AppCompatActivity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();

                    if (itemId == R.id.navigation_home) {
                        Intent homeIntent = new Intent(activity, MainActivity.class);
                        activity.startActivity(homeIntent);
                        Log.d(TAG, "Clicked on Home");
                        return true;
                    } else if (itemId == R.id.navigation_post) {
                        // Check if user is logged in
                        if (CurrentUser.getInstance().isLoggedIn()) {
                            Intent postIntent = new Intent(activity, InsertPost.class);
                            activity.startActivity(postIntent);
                            Log.d(TAG, "Started activity for inserting new post.");
                        } else {
                            // User is not logged in, redirect to login page
                            Intent loginIntent = new Intent(activity, Login.class);
                            activity.startActivity(loginIntent);
                            Log.d(TAG, "User not logged in, redirected to login page.");
                        }
                        return true;
                    } else if (itemId == R.id.navigation_profile) {
                        Intent profileIntent = new Intent(activity, Login.class);
                        activity.startActivity(profileIntent);
                        Log.d(TAG, "Started profile activity.");
                        return true;
                    }

                    return false;
                }
            });
        }
    }
}
