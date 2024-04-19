package com.example.socialmedia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Update extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDescription;
    private Button buttonUpdate;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize EditText and Button
        editTitle = findViewById(R.id.edit_tittle_update);
        editDescription = findViewById(R.id.edit_desc_update);
        buttonUpdate = findViewById(R.id.bt_update);

        // Receive the position of the item to be updated from the previous activity
        itemPosition = getIntent().getIntExtra("itemPosition", -1);

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

                // Update data in the database
                MyBD myBD = new MyBD(Update.this, 2); // Pass the context and database version
                int rowsAffected = myBD.atualizarPost(updatedTitle, updatedDescription, originalTitle);

                if (rowsAffected > 0) {
                    // If update was successful, show a Toast message
                    Toast.makeText(Update.this, "Item at position " + itemPosition + " updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("UpdateActivity", "Item at position " + itemPosition + " updated successfully!");
                } else {
                    // Otherwise, show an error message
                    Toast.makeText(Update.this, "Failed to update item at position " + itemPosition, Toast.LENGTH_SHORT).show();
                    Log.e("UpdateActivity", "Failed to update item at position " + itemPosition);
                }

                // Close the current activity
                finish();
            }
        });
    }
    }
