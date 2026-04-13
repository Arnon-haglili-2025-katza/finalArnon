package com.example.arnonfinalhta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private TextView tvWelcome, tvEmail, tvGender, tvBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        FrameLayout container = findViewById(R.id.content_container);

        View contentView = getLayoutInflater().inflate(
                R.layout.activity_profile,
                container,
                false
        );

        container.removeAllViews();
        container.addView(contentView);

        setupBottomNav(R.id.nav_profile);
        tvWelcome = contentView.findViewById(R.id.tvWelcome);
        tvEmail = contentView.findViewById(R.id.tvEmail);
        tvGender = contentView.findViewById(R.id.tvGender);
        tvBirthDate = contentView.findViewById(R.id.tvBirthDate);

        setupBottomNav(R.id.nav_profile);

        setupBottomNavigation();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadUserData();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setSelectedItemId(R.id.nav_profile);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (id == R.id.nav_table) {
                startActivity(new Intent(this, LeagueTableActivity.class));
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

        db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        String email = documentSnapshot.getString("email");
                        String gender = documentSnapshot.getString("gender");
                        String birthDate = documentSnapshot.getString("birthDate");

                        if (email != null) {
                            tvWelcome.setText("ברוך הבא, " + email.split("@")[0]);
                            tvEmail.setText("אימייל: " + email);
                        }

                        tvGender.setText("מגדר: " + (gender != null ? gender : "לא צוין"));
                        tvBirthDate.setText("תאריך לידה: " + (birthDate != null ? birthDate : "לא צוין"));
                    }
                });
    }
}
