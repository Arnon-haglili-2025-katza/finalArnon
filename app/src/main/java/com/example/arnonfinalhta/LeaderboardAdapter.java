package com.example.arnonfinalhta;

import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    List<User> users;

    public LeaderboardAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = users.get(position);

        String email = user.getEmail() != null ? user.getEmail() : "לא ידוע";

        holder.email.setText(email);
        holder.score.setText("נקודות: " + user.getScore());

        // טופ 3
        if (position == 0) holder.email.setText("🥇 " + email);
        if (position == 1) holder.email.setText("🥈 " + email);
        if (position == 2) holder.email.setText("🥉 " + email);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView email, score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.etEmail);
            score = itemView.findViewById(R.id.score);
        }
    }
}