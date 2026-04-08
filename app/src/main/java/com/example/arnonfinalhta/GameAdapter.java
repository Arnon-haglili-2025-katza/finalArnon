package com.example.arnonfinalhta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    List<Game> games;

    public GameAdapter(List<Game> games) {
        this.games = games;
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView opponent, dateTime, score;

        public GameViewHolder(View itemView) {
            super(itemView);
            opponent = itemView.findViewById(R.id.tvOpponent);
            dateTime = itemView.findViewById(R.id.tvDateTime);
            score = itemView.findViewById(R.id.tvScore);
        }
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Game game = games.get(position);

        holder.opponent.setText("הפועל ת\"א נגד " + game.opponent);
        holder.dateTime.setText(game.date + " | " + game.time);
        holder.score.setText(game.score);

        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(
                    v.getContext(),
                    GameDetailsActivity.class
            );

            intent.putExtra("opponent", game.opponent);
            intent.putExtra("date", game.date);
            intent.putExtra("time", game.time);
            intent.putExtra("score", game.score);

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}