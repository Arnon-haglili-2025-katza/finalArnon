package com.example.arnonfinalhta;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNav(int selectedItemId) {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (id == R.id.nav_matches) {
                startActivity(new Intent(this, MatchesActivity.class));
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
            } else if (id == R.id.nav_ai) {
                if (!(this instanceof AiActivity))
                    startActivity(new Intent(this, AiActivity.class));
            } else if (id == R.id.nav_trivia) {
                startActivity(new Intent(this, TriviaAiActivity.class));
            }

            overridePendingTransition(0, 0);
            return true;
        });
    }
}
