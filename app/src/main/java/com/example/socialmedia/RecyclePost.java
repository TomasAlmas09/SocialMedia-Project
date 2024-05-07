package com.example.socialmedia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclePost extends RecyclerView.Adapter<RecyclePost.ViewHolder> {
    private static final String TAG = "RecyclePost"; // Tag for logging

    private Context context;
    private List<Post> posts;
    private IOnSacaFoto listener;
    private String username;

    // Constructor to initialize RecyclePost adapter with a list of posts
    public RecyclePost(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    // Method to set the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Interface to handle image selection
    public void setOnSacaFotoListener(IOnSacaFoto listener) {
        this.listener = listener;
    }

    // ViewHolder class to hold the views
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView editId;
        TextView editModelo, editUser;
        ImageView imgFoto, imgUser;
        Button btnDelete;
        Button btnUpdate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            editId = itemView.findViewById(R.id.edit_idcar_itempost);
            editModelo = itemView.findViewById(R.id.edit_modelo_itempost);
            imgFoto = itemView.findViewById(R.id.img_foto_itempost);
            editUser = itemView.findViewById(R.id.edit_username);
        }
    }

    // Method to set the list of posts
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Inflate the itempost layout and return a new ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempost, parent, false);
        return new ViewHolder(itemView);
    }

    // Bind data to the views
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        // Set post data to the views
        holder.editId.setText(String.valueOf(post.getTitle()));
        holder.editModelo.setText(post.getModelo());
        holder.editUser.setText(post.getUsername());

        // Set the photo of the post if available
        if (post.getFoto() != null && post.getFoto().length > 0) {
            holder.imgFoto.setImageBitmap(Post.arrayToBitmap(post.getFoto()));
        } else {
            // Clear the image if there is no photo for this post
            holder.imgFoto.setImageBitmap(null);
        }

        // Log the binding of data for debugging
        Log.d(TAG, "Data bound for post at position: " + position);
    }

    // Return the number of posts
    @Override
    public int getItemCount() {
        return posts.size();
    }
}
