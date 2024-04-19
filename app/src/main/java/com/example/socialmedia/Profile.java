package com.example.socialmedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    RecycleUserPost adpt;
    // Position of the selected post
    public int pos;
    public static final int CANALINSERT = 2;
    // Request code for selecting a photo
    public static final int CANAL_FOTO = 1;
    // RecyclerView to display posts
    RecyclerView recyclerView;

    // Tag for logging
    private static final String TAG = "Profile";
    // Instantiate BottomBar
    BottomBar bottomBar;

    @Override
    protected void onResume() {
        super.onResume();
        // Load user posts every time the activity is resumed
        loadUserPosts();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set up BottomBar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar

        // Get the username from CurrentUser and set it to edit_username TextView
        TextView editUsername = findViewById(R.id.edit_username);
        String username = CurrentUser.getInstance().getUsername();

        // Check if the username is null or empty
        if (username != null && !username.isEmpty()) {
            editUsername.setText(username);
            // Load user posts
            loadUserPosts();
        } else {
            Log.e(TAG, "Username is null or empty.");
            // Handle the case where the username is null or empty
            // For example, you might redirect the user to the login screen or display an error message
        }
    }

    private void loadUserPosts() {
        // Call the method to load user posts from the database
        App.loadListUser();

        // Initialize RecyclerView and adapter after loading user posts
        initializeRecyclerView();

        // Set the user photo to ImageView
        if (!App.user.isEmpty()) {
            byte[] userPhotoBytes = App.user.get(0).getUserPhoto(); // Assuming the user photo is at index 0
            ImageView photoImageView = findViewById(R.id.photo);
            if (photoImageView != null && userPhotoBytes != null) {
                Log.d(TAG, "User photo bytes length: " + userPhotoBytes.length); // Log the length of user photo bytes
                Bitmap userPhotoBitmap = BitmapFactory.decodeByteArray(userPhotoBytes, 0, userPhotoBytes.length);
                if (userPhotoBitmap != null) {
                    photoImageView.setImageBitmap(userPhotoBitmap);
                } else {
                    Log.e(TAG, "Failed to decode user photo bytes into Bitmap.");
                }
            } else {
                Log.e(TAG, "ImageView or user photo bytes is null.");
            }
        } else {
            Log.e(TAG, "User list is empty.");
        }
    }

    private void initializeRecyclerView() {
        Log.e(TAG, "RecyclerView initialized.");

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recycle_post_profile);
        if (recyclerView == null) {
            Log.e(TAG, "RecyclerView not found in the layout.");
            return;
        }

        Log.e(TAG, "RecyclerView found.");

        if (App.user == null || App.user.isEmpty()) {
            Log.e(TAG, "List of posts is empty or null.");
            return;
        }

        Log.e(TAG, "List of posts is not empty.");

        adpt = new RecycleUserPost(App.user); // Use only the list of posts as argument
        if (adpt == null) {
            Log.e(TAG, "Adapter could not be initialized.");
            return;
        }

        Log.e(TAG, "Adapter initialized successfully.");

        recyclerView.setAdapter(adpt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.e(TAG, "LayoutManager configured for RecyclerView.");

        // Set the user photo to ImageView
        if (!App.user.isEmpty()) {
            byte[] userPhotoBytes = App.user.get(0).getUserPhoto(); // Assuming the user photo is at index 0
            ImageView photoImageView = findViewById(R.id.photo);
            if (photoImageView != null && userPhotoBytes != null) {
                Log.d(TAG, "User photo bytes length: " + userPhotoBytes.length); // Log the length of user photo bytes
                Bitmap userPhotoBitmap = BitmapFactory.decodeByteArray(userPhotoBytes, 0, userPhotoBytes.length);
                if (userPhotoBitmap != null) {
                    photoImageView.setImageBitmap(userPhotoBitmap);
                } else {
                    Log.e(TAG, "Failed to decode user photo bytes into Bitmap.");
                }
            } else {
                Log.e(TAG, "ImageView or user photo bytes is null.");
            }
        } else {
            Log.e(TAG, "User list is empty.");
        }


        // Set the click listener for delete button
        adpt.setOnItemClickListener(new RecycleUserPost.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deletePost(position);
            }

            @Override
            public void onUpdateClick(int position) {
                // Redirect to the Update activity and pass the item position as an extra
                Intent intent = new Intent(Profile.this, Update.class);
                intent.putExtra("itemPosition", position);
                Log.e(TAG, "Position: " + position);
                startActivity(intent);
            }
        });
    }

    // Delete a post from the list and database
    private void deletePost(int position) {
        if (App.user != null && position >= 0 && position < App.user.size()) {
            Log.e(TAG, "Position: " + position);
            String title = App.user.get(position).getTitle();
            App.user.remove(position);
            adpt.notifyItemRemoved(position);
            MyBD myBD = new MyBD(this, 2); // Use the appropriate version here
            myBD.deletePost(title); // Call deletePost to remove from the database
        }
    }
}
