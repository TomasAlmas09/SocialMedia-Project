package com.example.socialmedia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import com.google.android.material.button.MaterialButton;

@SuppressWarnings("ResourceType")
public class Login extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = "LoginActivity";
    // Database helper instance
    private MyBD myBD;

    // Views
    private TextView usernameTextView, passwordTextView;
    private MaterialButton loginButton, registerButton;
    // Instantiate BottomBar
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Instantiate BottomBar and setup bottom navigation bar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar
        // Initialize database helper
        myBD = new MyBD(this, 2);

        // Initialize views
        usernameTextView = findViewById(R.id.username);
        passwordTextView = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        registerButton = findViewById(R.id.registerbtn);

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username and password from text fields
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                Log.d(TAG, "Login button clicked");
                // Check if user credentials are valid
                if (myBD.loginUser(username, password)) {
                    // If login is successful, set current user and navigate to MainActivity
                    Log.d(TAG, "Login successful");
                    CurrentUser currentUser = CurrentUser.getInstance();
                    currentUser.setUsername(username);
                    currentUser.setPhoto("image/*".getBytes());
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Login.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    // Proceed with further steps after successful login
                } else {
                    // If login fails, display a toast message
                    Log.d(TAG, "Login failed");
                    Toast.makeText(Login.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Register activity
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
