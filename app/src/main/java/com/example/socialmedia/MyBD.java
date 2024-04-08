package com.example.socialmedia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyBD extends SQLiteOpenHelper {
    // Database name and table names
    public static final String DATABASENAME = "socialmedia.db";
    public static final String TB_POST = "tb_post";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String FOTO = "foto";
    private static final String TB_USER = "tb_user";
    private static final String USERNAME = "username";
    private static final String USER_PHOTO = "user_photo";
    private static final String PASSWORD = "password";
    private static final String TAG = "MyBD";

    // Constructor
    public MyBD(@Nullable Context context, int version) {
        super(context, DATABASENAME, null, version);
    }

    // Create database tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(TAG, "onCreate method called");
            String sql = "CREATE TABLE " + TB_POST + " ( " +
                    TITLE + " TEXT PRIMARY KEY, " +
                    USERNAME + " TEXT, " +
                    USER_PHOTO + " BLOB, " +
                    DESCRIPTION + " TEXT, " +
                    FOTO + " BLOB, " +
                    "FOREIGN KEY (" + USERNAME + ") REFERENCES " + TB_USER + "(" + USERNAME + ")" +
                    ");";
            db.execSQL(sql);
            Log.d(TAG, "Post table created.");

            String userTableSql = "CREATE TABLE " + TB_USER +
                    "(" + USERNAME + " TEXT PRIMARY KEY, " +
                    USER_PHOTO + " BLOB, " + // Define USER_PHOTO column as BLOB
                    PASSWORD + " TEXT)";
            db.execSQL(userTableSql);
            Log.d(TAG, "User table created.");
        } catch (SQLException e) {
            Log.e(TAG, "Error creating tables: " + e.getMessage());
        }
    }

    // Upgrade database tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TB_USER);
        onCreate(db);
        Log.d(TAG, "Database table upgraded.");
    }

    // Insert a new post into the database
    public long insertPost(Post novo, Bitmap bmp) {
        long resp = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(TITLE, novo.getTitle());
            cv.put(USERNAME, novo.getUsername());
            cv.put(USER_PHOTO, novo.getUserPhoto()); // Use the byte array directly
            cv.put(DESCRIPTION, novo.getModelo());
            cv.put(FOTO, Post.bitmapToArray(bmp));
            resp = db.insert(TB_POST, null, cv);
            db.setTransactionSuccessful();
            Log.d(TAG, "Inserted new post into database.");
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting data into database: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return resp;
    }


    // Register a new user in the database
    public void registerUser(String username, String password, Bitmap userPhoto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(PASSWORD, password);
        values.put(USER_PHOTO, Post.bitmapToArray(userPhoto));
        db.insert(TB_USER, null, values);
        db.close();
    }

    // Check if a username already exists in the database
    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {USERNAME};
        String selection = USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TB_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // Check login credentials
    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USERNAME};
        String selection = USERNAME + " = ? AND " + PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = db.query(TB_USER, columns, selection, selectionArgs, null, null, null);
            count = cursor.getCount();
        } catch (SQLException e) {
            Log.e(TAG, "Error querying login credentials: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return count > 0;
    }

    // Load the list of posts from the database
    public List<Post> carregaLista() {
        List<Post> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TB_POST;
        Cursor cur = db.rawQuery(sql, null);
        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                Post c = new Post(
                        cur.getString(0),
                        cur.getString(1),
                        cur.getString(3),
                        Post.arrayToBitmap(cur.getBlob(4)),
                        Post.arrayToBitmap(cur.getBlob(2))
                );
                lista.add(c);
            } while (cur.moveToNext());
            Log.d(TAG, "Loaded post list from database.");
        }
        cur.close();
        db.close();
        return lista;
    }
}
