package com.example.socialmedia;

public class CurrentUser {
    private static CurrentUser instance;
    private String username;
    private byte[] photo;

    private CurrentUser() {
        // Private constructor to prevent instantiation
    }

    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    // Method to check if the user is logged in
    public boolean isLoggedIn() {
        // Check if the username has been set to determine if the user is logged in
        return getUsername() != null && !getUsername().isEmpty();
    }
}

