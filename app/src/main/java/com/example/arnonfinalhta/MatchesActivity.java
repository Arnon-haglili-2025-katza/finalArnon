package com.example.arnonfinalhta;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MatchesActivity extends AppCompatActivity {

    private TextView tvMonthYear;
    private RecyclerView recyclerViewCalendar;
    private ImageButton btnPrevMonth, btnNextMonth;

    private LocalDate selectedDate;
    private ArrayList<LocalDate> daysInMonth;
    private HashMap<String, String> matchesMap;
    private CalendarAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        initViews();
        initMatches();
        setMonthView();
    }

    private void initViews() {
        tvMonthYear = findViewById(R.id.tvMonthYear);
        recyclerViewCalendar = findViewById(R.id.recyclerViewCalendar);
        btnPrevMonth = findViewById(R.id.btnPrevMonth);
        btnNextMonth = findViewById(R.id.btnNextMonth);

        selectedDate = LocalDate.now();

        recyclerViewCalendar.setLayoutManager(new GridLayoutManager(this, 7));

        btnPrevMonth.setOnClickListener(v -> prevMonth());
        btnNextMonth.setOnClickListener(v -> nextMonth());
    }

    private void initMatches() {
        matchesMap = new HashMap<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("matches");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchesMap.clear();

                for (DataSnapshot child : snapshot.getChildren()) {
                    String date = child.getKey();  // yyyy-MM-dd

                    String opponent = child.child("opponent").getValue(String.class);
                    String location = child.child("location").getValue(String.class);
                    String time = child.child("time").getValue(String.class);

                    if (opponent != null && location != null && time != null) {
                        String details = opponent + " – " + location + ", " + time;
                        matchesMap.put(date, details);
                    }
                }

                if (calendarAdapter != null) {
                    calendarAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    private void setMonthView() {
        tvMonthYear.setText(selectedDate.getMonth().toString() + " " + selectedDate.getYear());
        daysInMonth = generateDaysForMonth(selectedDate);
        calendarAdapter = new CalendarAdapter(this, daysInMonth, matchesMap);
        recyclerViewCalendar.setAdapter(calendarAdapter);
    }

    private ArrayList<LocalDate> generateDaysForMonth(LocalDate date) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int daysInMonth = date.lengthOfMonth();
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i < dayOfWeek; i++) {
            days.add(null);
        }

        for (int i = 1; i <= daysInMonth; i++) {
            days.add(LocalDate.of(date.getYear(), date.getMonth(), i));
        }

        return days;
    }

    private void prevMonth() {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    private void nextMonth() {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }
}
