package com.example.socialmedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private MyBD myBD;
    private EditText usernameEditText, passwordEditText;
    BottomBar bottomBar; // Instantiate BottomBar
    ImageView imgfoto;
    private static final int CANALFOTO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Instantiate BottomBar and setup bottom navigation bar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar

        myBD = new MyBD(this, 2); // Initialize MyBD instance

        usernameEditText = findViewById(R.id.username_register);
        passwordEditText = findViewById(R.id.password_register);
        imgfoto = findViewById(R.id.img_foto_register);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start activity for selecting image from gallery
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto, CANALFOTO);
                Log.d(TAG, "Started activity for image selection.");
            }
        });

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(); // Call registerUser method when the button is clicked
            }
        });
    }

    // Handle the result of selecting an image from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CANALFOTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                // Convert URI to bitmap and set it to the ImageView
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgfoto.setImageBitmap(bmp);
                Log.d(TAG, "Image set successfully from onActivityResult.");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error setting image from onActivityResult: " + e.getMessage());
            }
        }
    }

    // Method to register a new user
    private void registerUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if the ImageView has a drawable
        if (imgfoto.getDrawable() == null) {
            Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the bitmap from ImageView
        BitmapDrawable drw = (BitmapDrawable) imgfoto.getDrawable();
        Bitmap bmp = drw.getBitmap();

        // Check if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert Bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Check if the username already exists in the database
        if (myBD.checkUsernameExists(username)) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register the user in the database
        myBD.registerUser(username, password, bmp);
        Log.d(TAG, "User registered: " + username);
        finish(); // Finish the current activity
    }
}
