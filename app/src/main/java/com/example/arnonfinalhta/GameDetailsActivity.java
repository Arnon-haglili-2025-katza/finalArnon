package com.example.arnonfinalhta;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        TextView opponent = findViewById(R.id.tvDetailOpponent);
        TextView date = findViewById(R.id.tvDetailDate);
        TextView score = findViewById(R.id.tvDetailScore);

        // קבלת נתונים מהמסך הקודם
        String opp = getIntent().getStringExtra("opponent");
        String d = getIntent().getStringExtra("date");
        String t = getIntent().getStringExtra("time");
        String s = getIntent().getStringExtra("score");

        opponent.setText("הפועל ת\"א נגד " + opp);
        date.setText(d + " | " + t);
        score.setText(s);
    }
}