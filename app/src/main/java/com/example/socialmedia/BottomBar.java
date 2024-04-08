package com.example.socialmedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// Class representing the bottom navigation bar functionality
public class BottomBar extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = "BottomBar";

    // Method to set up the bottom navigation bar
    public void setupBottomBar(AppCompatActivity activity) {
        // Find the BottomNavigationView from the activity layout
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            // Set a listener for bottom navigation item selection
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Get the ID of the selected navigation item
                    int itemId = item.getItemId();

                    // Handle different navigation item clicks
                    if (itemId == R.id.navigation_home) {
                        // Start the MainActivity when Home is clicked
                        Intent homeIntent = new Intent(activity, MainActivity.class);
                        activity.startActivity(homeIntent);
                        // Log the event
                        Log.d(TAG, "Clicked on Home");
                        return true;
                    } else if (itemId == R.id.navigation_post) {
                        // Check if the user is logged in
                        if (CurrentUser.getInstance().isLoggedIn()) {
                            // Start the InsertPost activity if the user is logged in
                            Intent postIntent = new Intent(activity, InsertPost.class);
                            activity.startActivity(postIntent);
                            // Log the event
                            Log.d(TAG, "Started activity for inserting new post.");
                        } else {
                            // Start the Login activity if the user is not logged in
                            Intent loginIntent = new Intent(activity, Login.class);
                            activity.startActivity(loginIntent);
                            // Log the event
                            Log.d(TAG, "User not logged in, redirected to login page.");
                        }
                        return true;
                    } else if (itemId == R.id.navigation_profile) {
                        // Start the Profile activity when Profile is clicked
                        Intent profileIntent = new Intent(activity, Login.class);
                        activity.startActivity(profileIntent);
                        // Log the event
                        Log.d(TAG, "Started profile activity.");
                        return true;
                    } else {
                        // Log if an unexpected navigation item is clicked
                        Log.d(TAG, "Unexpected navigation item clicked: " + item.getTitle());
                    }

                    return false;
                }
            });
        } else {
            // Log an error if BottomNavigationView is not found in the layout
            Log.e(TAG, "BottomNavigationView not found in the layout.");
        }
    }
}
