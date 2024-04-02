package com.example.socialmedia;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static List<Post> stand = new ArrayList<>();
    public static Context ctx;
    public static final String TAG = "Xpto";

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        Log.d(TAG, "App Created");
        loadList();
        for (Post c : stand) Log.d(TAG, c.toString());
    }

    public static void loadList() {
        if (ctx != null) {
            MyBD myBD = new MyBD(ctx, 1);
            stand = myBD.carregaLista();
        } else {
            Log.e(TAG, "Context is null. Unable to load list.");
        }
    }
}
