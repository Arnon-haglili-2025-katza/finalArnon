package com.example.arnonfinalhta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends BaseActivity  {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private TextView tvWelcome, tvEmail, tvGender, tvBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_with_bottom_nav);
        getLayoutInflater().inflate(R.layout.activity_ai,
                findViewById(R.id.content_container), true);
        setupBottomNav(R.id.nav_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            else if (id == R.id.nav_matches) {
                startActivity(new Intent(this, MatchesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvWelcome = findViewById(R.id.tvWelcome);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);
        tvBirthDate = findViewById(R.id.tvBirthDate);

        loadUserData();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_matches) {
                startActivity(new Intent(this, MatchesActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }

    private void loadUserData() {
        if (mAuth.getCurrentUser() == null) return;

        String uid = mAuth.getCurrentUser().getUid();
        db.collection("users").document(uid).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String email = documentSnapshot.getString("email");
                String gender = documentSnapshot.getString("gender");
                String birthDate = documentSnapshot.getString("birthDate");

                tvWelcome.setText("ברוך הבא, " + email.split("@")[0]);
                tvEmail.setText("אימייל: " + email);
                tvGender.setText("מגדר: " + (gender != null ? gender : "לא צוין"));
                tvBirthDate.setText("תאריך לידה: " + (birthDate != null ? birthDate : "לא צוין"));
            }
        });
    }
}
