package com.example.arnonfinalhta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    ArrayList<NewsItem> list;

    public NewsAdapter(ArrayList<NewsItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = list.get(position);

        holder.title.setText(item.title);
        holder.desc.setText(item.description);
        holder.date.setText(item.date);

        Glide.with(holder.itemView.getContext())
                .load(item.imageUrl)
                .centerCrop()
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Context c = v.getContext();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url));
            c.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, desc, date;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgNews);
            title = itemView.findViewById(R.id.txtTitle);
            desc = itemView.findViewById(R.id.txtDescription);
            date = itemView.findViewById(R.id.txtDate);
        }
    }
}
