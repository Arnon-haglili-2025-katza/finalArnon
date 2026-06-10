package com.example.arnonfinalhta;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class LeagueTableActivity extends BaseActivity {

    private static final String ADMIN_EMAIL = "tori@gmail.com";

    private RecyclerView recyclerView;
    private TableAdapter adapter;
    private ArrayList<Team> teams = new ArrayList<>();

    private Button btnAddTeam;

    private DatabaseReference tableRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        FrameLayout container = findViewById(R.id.content_container);

        View view = getLayoutInflater().inflate(
                R.layout.activity_league_table,
                container,
                false
        );

        container.removeAllViews();
        container.addView(view);

        setupBottomNav(R.id.nav_table);

        recyclerView = view.findViewById(R.id.recyclerTable);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddTeam = view.findViewById(R.id.btnAddTeam);

        adapter = new TableAdapter(teams);
        recyclerView.setAdapter(adapter);

        tableRef = FirebaseDatabase.getInstance().getReference("leagueTable");

        setupAdminButton();
        loadTableFromFirebase();
    }

    private void setupAdminButton() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null &&
                ADMIN_EMAIL.equals(auth.getCurrentUser().getEmail())) {

            btnAddTeam.setVisibility(View.VISIBLE);

            btnAddTeam.setOnClickListener(v -> showAddOrUpdateTeamDialog());

        } else {
            btnAddTeam.setVisibility(View.GONE);
        }
    }

    private void loadTableFromFirebase() {

        tableRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                teams.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Team team = data.getValue(Team.class);

                    if (team != null) {
                        teams.add(team);
                    }
                }

                Collections.sort(teams, (a, b) -> a.position - b.position);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(
                        LeagueTableActivity.this,
                        "שגיאה בטעינת הטבלה: " + error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void showAddOrUpdateTeamDialog() {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 10);

        EditText etPosition = new EditText(this);
        etPosition.setHint("מיקום בטבלה לדוגמה: 1");
        etPosition.setSingleLine(true);
        etPosition.setInputType(InputType.TYPE_CLASS_NUMBER);

        EditText etName = new EditText(this);
        etName.setHint("שם הקבוצה");
        etName.setSingleLine(true);

        EditText etPoints = new EditText(this);
        etPoints.setHint("נקודות");
        etPoints.setSingleLine(true);
        etPoints.setInputType(InputType.TYPE_CLASS_NUMBER);

        EditText etGoalDiff = new EditText(this);
        etGoalDiff.setHint("הפרש שערים לדוגמה: 5 או -3");
        etGoalDiff.setSingleLine(true);
        etGoalDiff.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);

        layout.addView(etPosition);
        layout.addView(etName);
        layout.addView(etPoints);
        layout.addView(etGoalDiff);

        new AlertDialog.Builder(this)
                .setTitle("הוספה / עדכון קבוצה")
                .setMessage("אם תכניס מיקום שכבר קיים, הקבוצה באותו מיקום תתעדכן.")
                .setView(layout)
                .setPositiveButton("שמור", (dialog, which) -> {

                    String positionText = etPosition.getText().toString().trim();
                    String name = etName.getText().toString().trim();
                    String pointsText = etPoints.getText().toString().trim();
                    String goalDiffText = etGoalDiff.getText().toString().trim();

                    if (positionText.isEmpty() || name.isEmpty()
                            || pointsText.isEmpty() || goalDiffText.isEmpty()) {

                        Toast.makeText(
                                this,
                                "חובה למלא את כל השדות",
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    try {
                        int position = Integer.parseInt(positionText);
                        int points = Integer.parseInt(pointsText);
                        int goalDiff = Integer.parseInt(goalDiffText);

                        Team team = new Team(position, name, points, goalDiff);

                        String key = "position_" + position;

                        tableRef.child(key).setValue(team)
                                .addOnSuccessListener(unused ->
                                        Toast.makeText(
                                                this,
                                                "הקבוצה נשמרה בהצלחה",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                )
                                .addOnFailureListener(e ->
                                        Toast.makeText(
                                                this,
                                                "שגיאה: " + e.getMessage(),
                                                Toast.LENGTH_LONG
                                        ).show()
                                );

                    } catch (NumberFormatException e) {
                        Toast.makeText(
                                this,
                                "מיקום, נקודות והפרש שערים חייבים להיות מספרים",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                })
                .setNegativeButton("ביטול", null)
                .show();
    }
}