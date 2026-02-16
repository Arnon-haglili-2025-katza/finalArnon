package com.example.arnonfinalhta;

import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNav(int selectedItemId) {

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home && !(this instanceof MainActivity))
                startActivity(new Intent(this, MainActivity.class));

            else if (id == R.id.nav_matches && !(this instanceof MatchesActivity))
                startActivity(new Intent(this, MatchesActivity.class));

            else if (id == R.id.nav_news && !(this instanceof NewsActivity))
                startActivity(new Intent(this, NewsActivity.class));

            else if (id == R.id.nav_ai && !(this instanceof AiActivity))
                startActivity(new Intent(this, AiActivity.class));

            else if (id == R.id.nav_profile && !(this instanceof ProfileActivity))
                startActivity(new Intent(this, ProfileActivity.class));

            overridePendingTransition(0,0);
            return true;
        });
    }
}
