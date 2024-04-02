package com.example.socialmedia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclePost extends RecyclerView.Adapter<RecyclePost.ViewHolder> {

    public Context ctx;
    public List<Post> post;

    private IOnSacaFoto listener;
    public  void setOnSacaFotoListener(IOnSacaFoto lst){
        this.listener=lst;
    }

    public RecyclePost(Context ctx, List<Post> post) {
        this.ctx = ctx;
        this.post = post;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EditText editid,editmodelo;
        Spinner spincat;

        ImageView imgfoto;

        Button btdel, btupdate;
        public ViewHolder(@NonNull View v) {
            super(v);
            editid=v.findViewById(R.id.edit_idcar_itemcarro);
            editmodelo=v.findViewById(R.id.edit_modelo_itemcarro);
            spincat=v.findViewById(R.id.spin_categoria_itemcarro);
            imgfoto=v.findViewById(R.id.img_foto_itemcarro);
            btdel=v.findViewById(R.id.bt_delete_itemcarro);
            btupdate=v.findViewById(R.id.bt_update_itemcarro);
        }
    }

    @NonNull
    @Override
    public RecyclePost.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.itempost,parent,false);
        return new RecyclePost.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclePost.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Post post = this.post.get(position);
        holder.editid.setText(String.valueOf(post.getId()));
        holder.editmodelo.setText(post.getModelo());
        if(post.getFoto().length>0){
            holder.imgfoto.setImageBitmap(Post.arraytobitmap(post.getFoto()));
        }
        holder.imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.SacaFoto(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return post.size();
    }
}