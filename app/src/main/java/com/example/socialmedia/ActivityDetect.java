package com.example.socialmedia;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class ActivityDetect extends Application {

    // Keeps track of the current activity
    private static Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        // Registering lifecycle callbacks to detect activity changes
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                // Set the current activity when it is created
                currentActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {
                // Set the current activity when it is started
                currentActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                // Set the current activity when it is resumed
                currentActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                // Nothing needed here
            }

            @Override
            public void onActivityStopped(Activity activity) {
                // Nothing needed here
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                // Nothing needed here
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // Nothing needed here
            }
        });
    }

    // Method to get the current activity
    public static Activity getCurrentActivity() {
        return currentActivity;
    }
}
