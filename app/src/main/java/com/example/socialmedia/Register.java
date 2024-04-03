package com.example.socialmedia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private MyBD myBD;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myBD = new MyBD(this,2); // Initialize MyBD instance

        usernameEditText = findViewById(R.id.username_register);
        passwordEditText = findViewById(R.id.password_register);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(); // Call registerUser method when the button is clicked
            }
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the username already exists in the database
        if (myBD.checkUsernameExists(username)) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register the user
        myBD.registerUser(username, password);
        Log.d(TAG, "User registered: " + username);

        // Optionally, you can automatically login the user after registration
        // For simplicity, let's assume the user is logged in automatically

        // Navigate to the MainActivity or LoginActivity
        // For example:
        // Intent intent = new Intent(Register.this, MainActivity.class);
        // startActivity(intent);
        finish(); // Finish the current activity
    }
}
