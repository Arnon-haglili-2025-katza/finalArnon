package com.example.arnonfinalhta;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        FrameLayout container = findViewById(R.id.content_container);

        View contentView = getLayoutInflater().inflate(
                R.layout.activity_main,
                container,
                false
        );

        container.removeAllViews();
        container.addView(contentView);

        setupBottomNav(R.id.nav_home);

        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView tvNextOpponent = contentView.findViewById(R.id.tvNextOpponent);
        TextView tvNextDate = contentView.findViewById(R.id.tvNextDate);

        List<Game> games = new ArrayList<>();

        games.add(new Game("מכבי חיפה", "12/04", "20:30", "2-1"));
        games.add(new Game("בית\"ר ירושלים", "18/04", "19:00", "1-1"));
        games.add(new Game("מכבי תל אביב", "25/04", "21:00", "TBD"));
        games.add(new Game("הפועל באר שבע", "30/04", "20:15", "TBD"));

        for (Game g : games) {
            if (g.score.equals("TBD")) {
                tvNextOpponent.setText("הפועל ת\"א נגד " + g.opponent);
                tvNextDate.setText(g.date + " | " + g.time);
                break;
            }
        }

        GameAdapter adapter = new GameAdapter(games);
        recyclerView.setAdapter(adapter);
    }
}