package com.example.arnonfinalhta;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchesActivity extends AppCompatActivity {

    private TextView tvMatchDetails, tvMonthYear;
    private RecyclerView recyclerViewCalendar;
    private CalendarAdapter calendarAdapter;
    private List<String> daysList;
    private Map<String, String> matchesMap;

    private int currentMonth = 11; // נניח נובמבר
    private int currentYear = 2025;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        tvMatchDetails = findViewById(R.id.tvMatchDetails);
        tvMonthYear = findViewById(R.id.tvMonthYear);
        recyclerViewCalendar = findViewById(R.id.recyclerViewCalendar);

        ImageButton btnPrevMonth = findViewById(R.id.btnPrevMonth);
        ImageButton btnNextMonth = findViewById(R.id.btnNextMonth);

        // רשימת משחקים לדוגמה
        matchesMap = new HashMap<>();
        matchesMap.put("2025-11-01", "הפועל ת״א vs מכבי חיפה\nבלומפילד, 20:30");
        matchesMap.put("2025-11-05", "הפועל ת״א vs בית״ר ירושלים\nטדי, 21:00");
        matchesMap.put("2025-11-10", "הפועל ת״א vs מ.ס אשדוד\nבלומפילד, 19:00");

        daysList = new ArrayList<>();
        generateDaysForMonth(currentYear, currentMonth);

        calendarAdapter = new CalendarAdapter(daysList, matchesMap, (date, matchInfo) -> tvMatchDetails.setText(matchInfo));
        recyclerViewCalendar.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerViewCalendar.setAdapter(calendarAdapter);

        updateMonthYearText();

        btnPrevMonth.setOnClickListener(v -> {
            if (currentMonth == 1) {
                currentMonth = 12;
                currentYear--;
            } else {
                currentMonth--;
            }
            generateDaysForMonth(currentYear, currentMonth);
            calendarAdapter.notifyDataSetChanged();
            updateMonthYearText();
        });

        btnNextMonth.setOnClickListener(v -> {
            if (currentMonth == 12) {
                currentMonth = 1;
                currentYear++;
            } else {
                currentMonth++;
            }
            generateDaysForMonth(currentYear, currentMonth);
            calendarAdapter.notifyDataSetChanged();
            updateMonthYearText();
        });
    }

    private void generateDaysForMonth(int year, int month) {
        daysList.clear();
        int daysInMonth;
        switch (month) {
            case 2: daysInMonth = 28; break; // לא מתחשב בשנה מעוברת כרגע
            case 4: case 6: case 9: case 11: daysInMonth = 30; break;
            default: daysInMonth = 31; break;
        }

        for (int day = 1; day <= daysInMonth; day++) {
            String date = String.format("%04d-%02d-%02d", year, month, day);
            daysList.add(date);
        }
    }

    private void updateMonthYearText() {
        tvMonthYear.setText(String.format("%04d-%02d", currentYear, currentMonth));
    }
}
