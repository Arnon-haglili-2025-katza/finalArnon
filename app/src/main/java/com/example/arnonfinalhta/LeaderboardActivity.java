package com.example.arnonfinalhta;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardActivity extends BaseActivity {

    RecyclerView recyclerView;
    LeaderboardAdapter adapter;
    ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        FrameLayout container = findViewById(R.id.content_container);
        View view = getLayoutInflater().inflate(R.layout.activity_leaderboard, container, false);

        container.removeAllViews();
        container.addView(view);

        setupBottomNav(R.id.nav_leaderboard);

        recyclerView = view.findViewById(R.id.recyclerLeaderboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LeaderboardAdapter(userList);
        recyclerView.setAdapter(adapter);

        loadLeaderboard();
    }

    private void loadLeaderboard() {

        FirebaseDatabase.getInstance()
                .getReference("users")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        userList.clear();

                        for (DataSnapshot data : snapshot.getChildren()) {

                            String email = data.child("email").getValue(String.class);
                            Long scoreLong = data.child("score").getValue(Long.class);

                            int score = scoreLong != null ? scoreLong.intValue() : 0;

                            userList.add(new User(email, score));
                        }

                        Collections.sort(userList, (a, b) -> b.score - a.score);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {}
                });
    }
}