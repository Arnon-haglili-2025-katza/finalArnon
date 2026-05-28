package com.example.arnonfinalhta;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🎵 מפעיל מוזיקה בכל האפליקציה
        MusicManager.startMusic(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MusicManager.resumeMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();

        MusicManager.pauseMusic();
    }

    protected void setupBottomNav(int selectedItemId) {

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        if (bottomNav == null)
            return;

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home && !(this instanceof MainActivity))
                startActivity(new Intent(this, MainActivity.class));

            else if (id == R.id.nav_table && !(this instanceof LeagueTableActivity))
                startActivity(new Intent(this, LeagueTableActivity.class));

            else if (id == R.id.nav_news && !(this instanceof NewsActivity))
                startActivity(new Intent(this, NewsActivity.class));

            else if (id == R.id.nav_trivia && !(this instanceof TriviaAiActivity))
                startActivity(new Intent(this, TriviaAiActivity.class));

            else if (id == R.id.nav_profile && !(this instanceof ProfileActivity))
                startActivity(new Intent(this, ProfileActivity.class));

            overridePendingTransition(0, 0);

            return true;
        });
    }
}