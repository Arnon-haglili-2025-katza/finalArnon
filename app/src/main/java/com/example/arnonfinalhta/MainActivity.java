package com.example.arnonfinalhta;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class MainActivity extends BaseActivity {

    private static final String ADMIN_EMAIL = "tori@gmail.com";

    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private ArrayList<Game> games = new ArrayList<>();

    private TextView tvNextOpponent, tvNextDate;
    private Button btnAddGame;

    private DatabaseReference gamesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        FrameLayout container = findViewById(R.id.content_container);

        View contentView = getLayoutInflater().inflate(
                R.layout.activity_main,
                container,
                false
        );

        container.removeAllViews();
        container.addView(contentView);

        setupBottomNav(R.id.nav_home);

        recyclerView = contentView.findViewById(R.id.recyclerGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvNextOpponent = contentView.findViewById(R.id.tvNextOpponent);
        tvNextDate = contentView.findViewById(R.id.tvNextDate);
        btnAddGame = contentView.findViewById(R.id.btnAddGame);

        adapter = new GameAdapter(games);
        recyclerView.setAdapter(adapter);

        gamesRef = FirebaseDatabase.getInstance().getReference("games");

        setupAdminButton();
        loadGamesFromFirebase();
    }

    private void setupAdminButton() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null &&
                ADMIN_EMAIL.equals(auth.getCurrentUser().getEmail())) {

            btnAddGame.setVisibility(View.VISIBLE);

            btnAddGame.setOnClickListener(v -> showAddGameDialog());

        } else {
            btnAddGame.setVisibility(View.GONE);
        }
    }

    private void loadGamesFromFirebase() {

        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                games.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Game game = data.getValue(Game.class);

                    if (game != null) {
                        games.add(game);
                    }
                }

                adapter.notifyDataSetChanged();
                updateUpcomingGame();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(
                        MainActivity.this,
                        "שגיאה בטעינת משחקים: " + error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void updateUpcomingGame() {

        if (games.isEmpty()) {
            tvNextOpponent.setText("אין משחקים להצגה");
            tvNextDate.setText("");
            return;
        }

        for (Game g : games) {
            if (g.score != null &&
                    (g.score.equalsIgnoreCase("TBD") ||
                            g.score.equals("טרם שוחק"))) {

                tvNextOpponent.setText("הפועל ת\"א נגד " + g.opponent);
                tvNextDate.setText(g.date + " | " + g.time);
                return;
            }
        }

        tvNextOpponent.setText("אין משחק קרוב כרגע");
        tvNextDate.setText("");
    }

    private void showAddGameDialog() {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 10);

        EditText etOpponent = new EditText(this);
        etOpponent.setHint("שם היריבה");
        etOpponent.setSingleLine(true);

        EditText etDate = new EditText(this);
        etDate.setHint("תאריך לדוגמה: 25/04");
        etDate.setSingleLine(true);

        EditText etTime = new EditText(this);
        etTime.setHint("שעה לדוגמה: 20:30");
        etTime.setSingleLine(true);
        etTime.setInputType(InputType.TYPE_CLASS_TEXT);

        EditText etScore = new EditText(this);
        etScore.setHint("תוצאה או TBD");
        etScore.setSingleLine(true);

        layout.addView(etOpponent);
        layout.addView(etDate);
        layout.addView(etTime);
        layout.addView(etScore);

        new AlertDialog.Builder(this)
                .setTitle("הוספת משחק חדש")
                .setView(layout)
                .setPositiveButton("שמור", (dialog, which) -> {

                    String opponent = etOpponent.getText().toString().trim();
                    String date = etDate.getText().toString().trim();
                    String time = etTime.getText().toString().trim();
                    String score = etScore.getText().toString().trim();

                    if (opponent.isEmpty() || date.isEmpty() || time.isEmpty()) {
                        Toast.makeText(this, "חובה למלא יריבה, תאריך ושעה", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (score.isEmpty()) {
                        score = "TBD";
                    }

                    Game game = new Game(opponent, date, time, score);

                    gamesRef.push().setValue(game)
                            .addOnSuccessListener(unused ->
                                    Toast.makeText(this, "המשחק נוסף בהצלחה", Toast.LENGTH_SHORT).show()
                            )
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "שגיאה: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                })
                .setNegativeButton("ביטול", null)
                .show();
    }
}