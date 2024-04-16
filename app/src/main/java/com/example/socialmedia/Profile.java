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
    RecycleUserPost adpt; // Alterado de RecyclePost para RecycleUserPost
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
    // ViewHolder class to hold the views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set up BottomBar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar

        // Get the username from CurrentUser and set it to edit_username TextView
        TextView editUsername = findViewById(R.id.edit_username); // Alterado de EditText para TextView
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
        Log.e(TAG, "RecyclerView inicializada.");

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recycle_post_profile);
        if (recyclerView == null) {
            Log.e(TAG, "RecyclerView não encontrada no layout.");
            return;
        }

        Log.e(TAG, "RecyclerView encontrada.");

        if (App.user == null || App.user.isEmpty()) {
            Log.e(TAG, "Lista de posts vazia ou nula.");
            return;
        }

        Log.e(TAG, "Lista de posts não está vazia.");

        adpt = new RecycleUserPost(Profile.this, App.user); // Alterado de RecyclePost para RecycleUserPost
        if (adpt == null) {
            Log.e(TAG, "Adaptador não pôde ser inicializado.");
            return;
        }

        Log.e(TAG, "Adaptador inicializado com sucesso.");

        recyclerView.setAdapter(adpt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.e(TAG, "LayoutManager configurado para RecyclerView.");

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
        adpt.setOnItemClickListener(new RecycleUserPost.OnItemClickListener() { // Alterado de RecyclePost.OnItemClickListener para RecycleUserPost.OnItemClickListener
            @Override
            public void onDeleteClick(int position) {
                deletePost(position);
            }
        });
    }

    private void deletePost(int position) {
        if (App.user != null && position >= 0 && position < App.user.size()) {
            App.user.remove(position);
            adpt.notifyItemRemoved(position);
        }
    }

}
