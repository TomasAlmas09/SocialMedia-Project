package com.example.socialmedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

public class Profile extends AppCompatActivity {

    // Adapter for the RecyclerView
    RecyclePost adpt;
    // RecyclerView to display posts
    RecyclerView recyclerView;

    // Tag for logging
    private static final String TAG = "Profile";
    // Instantiate BottomBar
    BottomBar bottomBar;
    // ViewHolder class to hold the views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set up BottomBar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recycle_post_profile);
        adpt = new RecyclePost(Profile.this, App.user);
        recyclerView.setAdapter(adpt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the username from CurrentUser and set it to edit_username TextView
        TextView editUsername = findViewById(R.id.edit_username); // Alterado de EditText para TextView
        String username = CurrentUser.getInstance().getUsername();
        if (username != null) {
            editUsername.setText(username);
        } else {
            Log.e(TAG, "Username is null.");
        }
    }
}
