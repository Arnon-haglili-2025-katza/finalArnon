package com.example.arnonfinalhta;

import android.os.Build;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MatchesActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private HashMap<String, String> matchesMap;
    private TextView tvMatchDetails;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        calendarView = findViewById(R.id.calendarView);
        tvMatchDetails = findViewById(R.id.tvMatchDetails);

        // רשימת משחקים – תוכל להחליף בתאריך אמיתי
        matchesMap = new HashMap<>();
        matchesMap.put("2025-11-01", "הפועל ת״א vs מכבי חיפה\n🏟 בלומפילד\n🕒 20:30");
        matchesMap.put("2025-11-05", "הפועל ת״א vs בית״ר ירושלים\n🏟 טדי\n🕒 21:00");
        matchesMap.put("2025-11-10", "הפועל ת״א vs מ.ס אשדוד\n🏟 בלומפילד\n🕒 19:00");

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String dateKey = String.format("%04d-%02d-%02d", year, (month + 1), dayOfMonth);
            if (matchesMap.containsKey(dateKey)) {
                tvMatchDetails.setText("📅 " + matchesMap.get(dateKey));
            } else {
                tvMatchDetails.setText("אין משחק בתאריך זה 🙂");
            }
        });
    }
}
