package com.example.arnonfinalhta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private final List<User> users;

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

        String email = user.name != null ? user.name : "משתמש";

        if (position == 0) {
            holder.email.setText("🥇 " + email);
        } else if (position == 1) {
            holder.email.setText("🥈 " + email);
        } else if (position == 2) {
            holder.email.setText("🥉 " + email);
        } else {
            holder.email.setText((position + 1) + ". " + email);
        }

        holder.score.setText(user.score + " נק'");
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView email, score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.email);
            score = itemView.findViewById(R.id.score);
        }
    }
}