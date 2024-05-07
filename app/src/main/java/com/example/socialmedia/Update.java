package com.example.socialmedia;

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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Update extends AppCompatActivity {
    private static final int CANALFOTO = 3;
    private EditText editTitle;
    private EditText editDescription;
    private Button buttonUpdate, btcancel;
    private int itemPosition;
    private static final String TAG = "UpdateActivity";
    ImageView imgfoto;

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
        setContentView(R.layout.activity_update);

        // Initialize EditText, Button, and ImageView
        editTitle = findViewById(R.id.edit_tittle_update);
        editDescription = findViewById(R.id.edit_desc_update);
        buttonUpdate = findViewById(R.id.bt_update);
        imgfoto = findViewById(R.id.img_foto_update);
        btcancel = findViewById(R.id.bt_cancel);

        // Receive the position of the item to be updated from the previous activity
        itemPosition = getIntent().getIntExtra("itemPosition", -1);

        // Attach click listener to ImageView for selecting image
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start activity for selecting image from gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CANALFOTO);
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log cancellation
                Log.d(TAG, "Update activity cancelled.");
                finish();
            }
        });

        // Set click listener for the update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated title and description
                String updatedTitle = editTitle.getText().toString();
                String updatedDescription = editDescription.getText().toString();

                // Check if title is provided
                if (updatedTitle.isEmpty()) {
                    editTitle.setError("Insira um título");
                    return;
                }

                // Check if description is provided
                if (updatedDescription.isEmpty()) {
                    editDescription.setError("Insira uma descrição");
                    return;
                }

                // Get the original title of the post
                String originalTitle = App.user.get(itemPosition).getTitle();

                // Get the updated image from the ImageView
                Bitmap updatedImage = getBitmapFromImageView(imgfoto);

                // Update data in the database
                MyBD myBD = new MyBD(Update.this, 2); // Pass the context and database version
                int rowsAffected = myBD.atualizarPost(updatedTitle, updatedDescription, originalTitle, updatedImage);

                if (rowsAffected > 0) {
                    // If update was successful, show a Toast message
                    Toast.makeText(Update.this, "Item at position " + itemPosition + " updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Item at position " + itemPosition + " updated successfully!");
                } else {
                    // Otherwise, show an error message
                    Toast.makeText(Update.this, "Failed to update item at position " + itemPosition, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to update item at position " + itemPosition);
                }

                // Close the current activity
                finish();
            }
        });
    }


    // Method to convert ImageView to Bitmap
    private Bitmap getBitmapFromImageView(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
