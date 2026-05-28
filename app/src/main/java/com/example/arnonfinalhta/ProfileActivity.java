package com.example.arnonfinalhta;

import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private TextView tvWelcome, tvEmail, tvGender, tvBirthDate;
    private Button btnLogout, btnResetPassword;

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

        btnLogout = contentView.findViewById(R.id.btnLogout);
        btnResetPassword = contentView.findViewById(R.id.btnResetPassword);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadUserData();

        btnLogout.setOnClickListener(v -> logoutUser());

        btnResetPassword.setOnClickListener(v ->
                startActivity(new Intent(this, ResetPasswordActivity.class))
        );
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

    private void logoutUser() {

        new AlertDialog.Builder(this)
                .setTitle("התנתקות")
                .setMessage("האם אתה בטוח שברצונך להתנתק?")
                .setPositiveButton("כן", (dialog, which) -> {

                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("ביטול", null)
                .show();
    }
}