package com.example.arnonfinalhta;

import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    List<User> list;

    public LeaderboardAdapter(List<User> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank, name, score;

        public ViewHolder(View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            name = itemView.findViewById(R.id.name);
            score = itemView.findViewById(R.id.score);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = list.get(position);

        holder.rank.setText(String.valueOf(position + 1));
        holder.name.setText(user.name);
        holder.score.setText(String.valueOf(user.score));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}