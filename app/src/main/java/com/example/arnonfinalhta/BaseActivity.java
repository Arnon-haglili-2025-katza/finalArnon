package com.example.arnonfinalhta;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    private NetworkChangeReceiver networkReceiver;
    private AlertDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MusicManager.startMusic(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        networkReceiver = new NetworkChangeReceiver(isConnected -> {
            if (isConnected) {
                hideNoInternetDialog();
            } else {
                showNoInternetDialog();
            }
        });

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);

        if (!NetworkChangeReceiver.isInternetAvailable(this)) {
            showNoInternetDialog();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (networkReceiver != null) {
            try {
                unregisterReceiver(networkReceiver);
            } catch (Exception ignored) {
            }

            networkReceiver = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MusicManager.resumeMusic();

        if (!NetworkChangeReceiver.isInternetAvailable(this)) {
            showNoInternetDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        MusicManager.pauseMusic();
    }

    private void showNoInternetDialog() {

        if (isFinishing()) return;

        if (noInternetDialog != null && noInternetDialog.isShowing()) {
            return;
        }

        noInternetDialog = new AlertDialog.Builder(this)
                .setTitle("אין חיבור לאינטרנט")
                .setMessage("האפליקציה דורשת חיבור לאינטרנט כדי לעבוד. בדוק את החיבור לרשת ונסה שוב.")
                .setCancelable(false)
                .setPositiveButton("נסה שוב", null)
                .create();

        noInternetDialog.setOnShowListener(dialog -> {
            noInternetDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener(v -> {
                        if (NetworkChangeReceiver.isInternetAvailable(this)) {
                            hideNoInternetDialog();
                        } else {
                            noInternetDialog.setMessage(
                                    "עדיין אין חיבור לאינטרנט. בדוק Wi-Fi או נתונים סלולריים ונסה שוב."
                            );
                        }
                    });
        });

        noInternetDialog.show();
    }

    private void hideNoInternetDialog() {
        if (noInternetDialog != null && noInternetDialog.isShowing()) {
            noInternetDialog.dismiss();
        }
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