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

    private static final String TAG = "LoginActivity";
    private MyBD myBD;

    private TextView usernameTextView, passwordTextView;
    private MaterialButton loginButton, registerButton;
    BottomBar bottomBar; // Instantiate BottomBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Instantiate BottomBar and setup bottom navigation bar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar
        myBD = new MyBD(this,2);

        usernameTextView = findViewById(R.id.username);
        passwordTextView = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);

        // Inicialize o botão de registro
        registerButton = findViewById(R.id.registerbtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                Log.d(TAG, "Login button clicked");
                if (myBD.loginUser(username, password)) {
                    Log.d(TAG, "Login successful");
                    CurrentUser currentUser = CurrentUser.getInstance();
                    currentUser.setUsername(username);
                    currentUser.setPhoto("image/*".getBytes());

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Login.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    // Proceed with further steps after successful login
                } else {
                    Log.d(TAG, "Login failed");
                    Toast.makeText(Login.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Defina o ouvinte de clique para o botão de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicie a atividade de registro
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
