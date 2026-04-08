package com.example.arnonfinalhta;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    List<Team> teams;

    public TableAdapter(List<Team> teams) {
        this.teams = teams;
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView position, team, points, goalDiff;

        public TableViewHolder(View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.tvPosition);
            team = itemView.findViewById(R.id.tvTeam);
            points = itemView.findViewById(R.id.tvPoints);
            goalDiff = itemView.findViewById(R.id.tvGoalDiff);
        }
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, int position) {
        Team t = teams.get(position);

        holder.position.setText(String.valueOf(t.position));
        holder.team.setText(t.name);
        holder.points.setText(String.valueOf(t.points));
        holder.goalDiff.setText((t.goalDiff > 0 ? "+" : "") + t.goalDiff);

        // 🔴 הדגשת הפועל ת״א
        if (t.name.equals("הפועל תל אביב")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFEBEE"));
            holder.team.setTextColor(Color.parseColor("#C62828"));
        }

        // 🟢 טופ 3
        if (t.position <= 3) {
            holder.position.setTextColor(Color.parseColor("#2E7D32"));
        }
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }
}