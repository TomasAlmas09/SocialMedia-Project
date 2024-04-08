package com.example.socialmedia;

// Class representing the current user of the application
public class CurrentUser {
    // Singleton instance of CurrentUser
    private static CurrentUser instance;
    // Username of the current user
    private String username;
    // Profile photo of the current user
    private byte[] photo;

    // Private constructor to prevent direct instantiation
    private CurrentUser() {
        // Private constructor to prevent instantiation
    }

    // Method to get the singleton instance of CurrentUser
    public static synchronized CurrentUser getInstance() {
        // Create a new instance if it's not already initialized
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    // Method to get the username of the current user
    public String getUsername() {
        return username;
    }

    // Method to set the username of the current user
    public void setUsername(String username) {
        this.username = username;
    }

    // Method to get the profile photo of the current user
    public byte[] getPhoto() {
        return photo;
    }

    // Method to set the profile photo of the current user
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    // Method to check if the user is logged in
    public boolean isLoggedIn() {
        // Check if the username has been set to determine if the user is logged in
        return getUsername() != null && !getUsername().isEmpty();
    }
}
