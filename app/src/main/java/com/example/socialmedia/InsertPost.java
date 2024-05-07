package com.example.socialmedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

// Activity for inserting a new post
public class InsertPost extends AppCompatActivity {

    private static final int CANALFOTO = 3;
    EditText editTitle, editDesc;
    Button btinsert, btcancel;
    ImageView imgfoto;

    // Tag for logging
    private static final String TAG = "InsertPost";
    // Instantiate BottomBar
    private BottomBar bottomBar;

    // Method to handle result from activity for selecting image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CANALFOTO && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri uri = data.getData();
            try {
                // Convert URI to bitmap and set it to the ImageView
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgfoto.setImageBitmap(bmp);
                // Log success message
                Log.d(TAG, "Image set successfully from onActivityResult.");
            } catch (IOException e) {
                e.printStackTrace();
                // Log error message if setting image fails
                Log.e(TAG, "Error setting image from onActivityResult: " + e.getMessage());
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        // Instantiate BottomBar and setup bottom navigation bar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar

        // Initialize views
        editTitle = findViewById(R.id.edit_tittle_insert);
        editDesc = findViewById(R.id.edit_desc_insert);
        imgfoto = findViewById(R.id.img_foto_insert);
        btinsert = findViewById(R.id.bt_insert_insert); // Initialize btinsert button

        // Set click listener for selecting image
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start activity for selecting image from gallery
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto, CANALFOTO);
                // Log the event
                Log.d(TAG, "Started activity for image selection.");
            }
        });

        // Set click listener for inserting new post
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get title, description, and bitmap from views
                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();

                // Check if imgfoto is not null before accessing its drawable
                if (imgfoto != null && imgfoto.getDrawable() != null) {
                    BitmapDrawable drw = (BitmapDrawable) imgfoto.getDrawable();
                    Bitmap postBmp = drw.getBitmap();

                    // Get username and user photo from CurrentUser instance
                    CurrentUser currentUser = CurrentUser.getInstance();
                    String username = currentUser.getUsername();
                    byte[] userPhotoByteArray = currentUser.getPhoto();

                    // Create a new Post object
                    Post novo = new Post(title, username, desc, postBmp, BitmapFactory.decodeByteArray(userPhotoByteArray, 0, userPhotoByteArray.length));

                    // Insert the post into the database
                    MyBD myBD = new MyBD(InsertPost.this, 2);
                    myBD.insertPost(novo, postBmp);

                    // Reload the post list
                    App.loadList();

                    // Start MainActivity
                    Intent intent = new Intent(InsertPost.this, MainActivity.class);
                    startActivity(intent);

                    // Log the event
                    Log.d(TAG, "Inserted new post and finished activity.");
                } else {
                    // Handle case where imgfoto is null or its drawable is null
                    Log.e(TAG, "ImageView imgfoto is null or its drawable is null.");
                    // You may want to show a message to the user or handle the situation differently
                }
            }
        });

        // Set click listener for canceling post insertion
        btcancel = findViewById(R.id.bt_cancel_insert);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity
                Intent intent = new Intent(InsertPost.this, MainActivity.class);
                startActivity(intent);
                // Log the event
                Log.d(TAG, "Cancelled post insertion and finished activity.");
            }
        });
    }

}
