package com.example.arnonfinalhta;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MatchesActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private HashMap<String, String> matchesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        calendarView = findViewById(R.id.calendarView);

        // רשימת משחקים – תוכל להחליף בתאריך אמיתי
        matchesMap = new HashMap<>();
        matchesMap.put("2025-11-01", "הפועל ת״א vs מכבי חיפה\nבלומפילד, 20:30");
        matchesMap.put("2025-11-05", "הפועל ת״א vs בית״ר ירושלים\nטדי, 21:00");
        matchesMap.put("2025-11-10", "הפועל ת״א vs מ.ס אשדוד\nבלומפילד, 19:00");

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String date = String.format("%04d-%02d-%02d", year, (month + 1), dayOfMonth);
            if (matchesMap.containsKey(date)) {
                Toast.makeText(
                        this,
                        "📅 " + matchesMap.get(date),
                        Toast.LENGTH_LONG
                ).show();
            } else {
                Toast.makeText(
                        this,
                        "אין משחק בתאריך זה 🙂",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
