package com.example.socialmedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;

public class Post implements Parcelable {
    // Tag for logging
    private static final String TAG = "Post";

    // Fields
    private String title;
    private String modelo;
    private String username;
    private byte[] foto;
    private byte[] userfoto;

    // Constructor for Parcelable
    protected Post(Parcel in) {
        title = in.readString();
        modelo = in.readString();
        username = in.readString();
        foto = in.createByteArray();
        userfoto = in.createByteArray();
    }

    // Method to write object data to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(modelo);
        dest.writeString(username);
        dest.writeByteArray(foto);
    }

    // Method to describe contents (not used here)
    @Override
    public int describeContents() {
        return 0;
    }

    // Creator for Parcelable
    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // toString method
    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", title, modelo);
    }

    // Constructor to accept Bitmap objects for photos
    public Post(String title, String username, String description, Bitmap postPhoto, Bitmap bmp) {
        this.title = title;
        this.username = username;
        this.modelo = description;
        this.foto = bitmapToArray(postPhoto);
    }

    // Method to convert Bitmap to byte array
    public static byte[] bitmapToArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // Method to convert byte array to Bitmap
    public static Bitmap arrayToBitmap(byte[] foto) {
        return BitmapFactory.decodeByteArray(foto, 0, foto.length);
    }
}
