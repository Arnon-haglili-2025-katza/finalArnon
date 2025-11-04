package com.example.arnonfinalhta;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSend;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // views
        etEmail = findViewById(R.id.etEmail);
        btnSend = findViewById(R.id.btnReset);
        progressBar = findViewById(R.id.progressBar);

        // firebase
        mAuth = FirebaseAuth.getInstance();

        btnSend.setOnClickListener(v -> sendResetEmail());
    }

    private void sendResetEmail() {
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Enter your email");
            etEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnSend.setEnabled(false);

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    btnSend.setEnabled(true);

                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this,
                                "Reset email sent. Check your inbox (or spam).",
                                Toast.LENGTH_LONG).show();
                        finish(); // לחזור למסך הקודם (לוגין)
                    } else {
                        // הצג הודעת שגיאה מפורשת כשיש
                        String msg = (task.getException() != null) ? task.getException().getMessage() : "Failed to send reset email";
                        Toast.makeText(ResetPasswordActivity.this, "Error: " + msg, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
