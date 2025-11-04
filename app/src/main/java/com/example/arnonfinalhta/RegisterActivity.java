package com.example.arnonfinalhta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etPasswordConfirm, etBirthDate;
    private Spinner spGender;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        etBirthDate = findViewById(R.id.etBirthDate);
        spGender = findViewById(R.id.spGender);
        progressBar = findViewById(R.id.progressBar);

        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnBack = findViewById(R.id.btnBack);

        btnRegister.setOnClickListener(v -> registerUser());
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etPasswordConfirm.getText().toString().trim();
        String birthDate = etBirthDate.getText().toString().trim();
        String gender = spGender.getSelectedItem().toString();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("חובה להזין אימייל");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            etPassword.setError("סיסמה חייבת להכיל לפחות 6 תווים");
            return;
        }
        if (!password.equals(confirmPassword)) {
            etPasswordConfirm.setError("הסיסמאות אינן תואמות");
            return;
        }
        if (TextUtils.isEmpty(birthDate)) {
            etBirthDate.setError("נא להזין תאריך לידה");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();

                        Map<String, Object> user = new HashMap<>();
                        user.put("email", email);
                        user.put("gender", gender);
                        user.put("birthDate", birthDate);

                        db.collection("users").document(uid)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "נרשמת בהצלחה!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, MainActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "שגיאה בשמירת הנתונים", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(this, "שגיאה בהרשמה: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
