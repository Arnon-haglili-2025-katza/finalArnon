package com.example.arnonfinalhta;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LeagueTableActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        android.view.View view = getLayoutInflater().inflate(
                R.layout.activity_league_table,
                findViewById(R.id.content_container),
                true
        );

        setupBottomNav(R.id.nav_table);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerTable);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Team> teams = new ArrayList<>();

        // טבלה ריאליסטית
        teams.add(new Team(1, "מכבי חיפה", 65, 30));
        teams.add(new Team(2, "מכבי תל אביב", 62, 25));
        teams.add(new Team(3, "הפועל באר שבע", 58, 18));
        teams.add(new Team(4, "בית\"ר ירושלים", 50, 10));
        teams.add(new Team(5, "מכבי נתניה", 48, 5));
        teams.add(new Team(6, "הפועל חיפה", 45, 2));
        teams.add(new Team(7, "בני סכנין", 40, -3));
        teams.add(new Team(8, "מ.ס אשדוד", 38, -5));
        teams.add(new Team(9, "הפועל תל אביב", 36, -6)); // 🔴 שלך
        teams.add(new Team(10, "הפועל ירושלים", 34, -10));
        teams.add(new Team(11, "בני ריינה", 30, -15));
        teams.add(new Team(12, "הפועל חדרה", 25, -20));
        teams.add(new Team(13, "מכבי פתח תקווה", 22, -25));
        teams.add(new Team(14, "סקציה נס ציונה", 18, -30));

        TableAdapter adapter = new TableAdapter(teams);
        recyclerView.setAdapter(adapter);
    }
}