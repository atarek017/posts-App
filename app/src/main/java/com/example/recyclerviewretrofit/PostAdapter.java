package com.example.recyclerviewretrofit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> mPostleList;


    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener lesitenr) {
        mListener = lesitenr;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView body;
        public ImageView mImage;

        public PostViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            mImage = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }


    public PostAdapter(ArrayList<Post> posts) {
        mPostleList = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
        PostViewHolder postViewHolder = new PostViewHolder(v, mListener);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        Post curntPost = mPostleList.get(i);

        postViewHolder.title.setText(curntPost.getTitle());
        postViewHolder.body.setText(curntPost.getText());
    }

    @Override
    public int getItemCount() {
        return mPostleList.size();
    }


}
