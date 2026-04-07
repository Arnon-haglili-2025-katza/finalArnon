package com.example.arnonfinalhta;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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

        // 👉 מכניס את הלייאאוט של הטבלה
        LinearLayout container = findViewById(R.id.content_container);
        View view = getLayoutInflater().inflate(R.layout.activity_leaderboard, container, false);
        container.addView(view);

        setupBottomNav(R.id.nav_profile);

        recyclerView = view.findViewById(R.id.recyclerLeaderboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LeaderboardAdapter(userList);
        recyclerView.setAdapter(adapter);

        loadLeaderboard();
    }

    private void loadLeaderboard() {

        FirebaseDatabase.getInstance()
                .getReference("users")
                .orderByChild("score")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        userList.clear();

                        for (DataSnapshot data : snapshot.getChildren()) {
                            User user = data.getValue(User.class);
                            if (user != null)
                                userList.add(user);
                        }

                        Collections.reverse(userList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {}
                });
    }
}