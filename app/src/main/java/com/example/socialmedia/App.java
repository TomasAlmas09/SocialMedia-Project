package com.example.socialmedia;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static List<Post> stand = new ArrayList<>();
    public static List<Post> user = new ArrayList<>();
    public static Context ctx;
    public static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        Log.d(TAG, "Context: " + ctx);
        Log.d(TAG, "App Created");

        // Load the list after the context has been initialized
        loadList();
        loadListUser();
    }

    public static void loadList() {
        if (ctx != null) {
            MyBD myBD = new MyBD(ctx, 2);
            stand = myBD.carregaLista();
            user = myBD.carregaListaUser();
            Log.d(TAG, "Lists loaded successfully.");
        } else {
            Log.e(TAG, "Context is null. Unable to load lists.");
        }
    }

    public static void loadListUser() {
        if (ctx != null) {
            MyBD myBD = new MyBD(ctx, 2);
            user = myBD.carregaListaUser();
            Log.d(TAG, "List User loaded successfully.");
        } else {
            String errorMessage = "Context is null. Unable to load list user.";
            Log.e(TAG, errorMessage);
            // You can add more details to the error message if needed
            // For example, you can include the stack trace or the class name
            Log.e(TAG, errorMessage, new NullPointerException("Context is null"));
        }
    }

}
