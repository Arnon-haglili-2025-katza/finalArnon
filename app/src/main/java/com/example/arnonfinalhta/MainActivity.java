package com.example.arnonfinalhta;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        getLayoutInflater().inflate(
                R.layout.activity_main,
                findViewById(R.id.content_container),
                true
        );

        setupBottomNav(R.id.nav_home);

        View contentView = getLayoutInflater().inflate(
                R.layout.activity_main,
                findViewById(R.id.content_container),
                true
        );

        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerGames);        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Game> games = new ArrayList<>();

// ====== סיבוב ראשון ======
        games.add(new Game("מכבי פתח תקווה", "26/08", "19:00", "2-0"));
        games.add(new Game("הפועל באר שבע", "02/09", "20:15", "1-1"));
        games.add(new Game("מכבי חיפה", "16/09", "20:30", "0-2"));
        games.add(new Game("בית\"ר ירושלים", "23/09", "19:30", "3-1"));
        games.add(new Game("מ.ס אשדוד", "30/09", "18:45", "2-2"));
        games.add(new Game("הפועל חיפה", "07/10", "20:00", "1-0"));
        games.add(new Game("בני סכנין", "21/10", "19:15", "2-1"));
        games.add(new Game("הפועל חדרה", "28/10", "18:30", "3-0"));
        games.add(new Game("מכבי נתניה", "04/11", "20:15", "1-2"));
        games.add(new Game("בני ריינה", "11/11", "19:00", "2-0"));
        games.add(new Game("הפועל ירושלים", "25/11", "20:00", "1-1"));

// ====== סיבוב שני ======
        games.add(new Game("מכבי פתח תקווה", "02/12", "19:00", "TBD"));
        games.add(new Game("הפועל באר שבע", "09/12", "20:30", "TBD"));
        games.add(new Game("מכבי חיפה", "16/12", "20:30", "TBD"));
        games.add(new Game("בית\"ר ירושלים", "23/12", "19:30", "TBD"));
        games.add(new Game("מ.ס אשדוד", "30/12", "18:45", "TBD"));
        games.add(new Game("הפועל חיפה", "06/01", "20:00", "TBD"));
        games.add(new Game("בני סכנין", "13/01", "19:15", "TBD"));
        games.add(new Game("הפועל חדרה", "20/01", "18:30", "TBD"));
        games.add(new Game("מכבי נתניה", "27/01", "20:15", "TBD"));
        games.add(new Game("בני ריינה", "03/02", "19:00", "TBD"));
        games.add(new Game("הפועל ירושלים", "10/02", "20:00", "TBD"));
        GameAdapter adapter = new GameAdapter(games);
        recyclerView.setAdapter(adapter);
    }
}