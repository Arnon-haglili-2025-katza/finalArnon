package com.example.arnonfinalhta;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNav(int selectedItemId) {

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav == null) return;

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            Intent intent = null;

            if (id == R.id.nav_home) {
                intent = new Intent(this, MainActivity.class);
            } else if (id == R.id.nav_matches) {
                intent = new Intent(this, MatchesActivity.class);
            } else if (id == R.id.nav_profile) {
                intent = new Intent(this, ProfileActivity.class);
            } else if (id == R.id.nav_ai) {
                intent = new Intent(this, AiActivity.class);
            } else if (id == R.id.nav_trivia) {
                intent = new Intent(this, TriviaAiActivity.class);
            } else if (id == R.id.nav_news) {
                intent = new Intent(this, NewsActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                overridePendingTransition(0, 0);
            }

            return true;
        });
    }
}
