package com.example.socialmedia;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    // Lists to hold posts
    public static List<Post> stand = new ArrayList<>();
    public static List<Post> user = new ArrayList<>();
    // Application context
    public static Context ctx;
    // Tag for logging
    public static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        // Get the application context
        ctx = getApplicationContext();
        // Log the context
        Log.d(TAG, "Context: " + ctx);
        // Log that the application is created
        Log.d(TAG, "App Created");

        // Load the list after the context has been initialized
        loadList();
        loadListUser();
    }

    // Method to load the standard list of posts
    public static void loadList() {
        if (ctx != null) {
            // Initialize database helper with the application context
            MyBD myBD = new MyBD(ctx, 2);
            // Load standard list of posts
            stand = myBD.carregaLista();
            // Log success message
            Log.d(TAG, "Standard list loaded successfully.");
        } else {
            // Log error if context is null
            Log.e(TAG, "Context is null. Unable to load standard list.");
        }
    }

    // Method to load the user-specific list of posts
    public static void loadListUser() {
        if (ctx != null) {
            // Initialize database helper with the application context
            MyBD myBD = new MyBD(ctx, 2);
            // Load user-specific list of posts
            user = myBD.carregaListaUser();
            // Log success message
            Log.d(TAG, "User list loaded successfully.");
        } else {
            // Log error if context is null
            String errorMessage = "Context is null. Unable to load user list.";
            Log.e(TAG, errorMessage);
            // You can add more details to the error message if needed
            // For example, you can include the stack trace or the class name
            Log.e(TAG, errorMessage, new NullPointerException("Context is null"));
        }
    }
}
