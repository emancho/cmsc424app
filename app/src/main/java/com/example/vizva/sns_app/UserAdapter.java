package com.example.vizva.sns_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<String> post;
    String TAG = "Adapter";

    public UserAdapter(ArrayList<String> post){
        this.post = post;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);

        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int i) {
        holder.titlePost.setText(post.get(i));
//        holder.articlePost.setText(post.get(i));

    }

    @Override
    public int getItemCount() {
        return post.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titlePost, articlePost;

        public ViewHolder(View itemView){
            super(itemView);
            titlePost = itemView.findViewById(R.id.titleofPost);
//            articlePost = itemView.findViewById(R.id.articleofPost);

        }
    }

}
