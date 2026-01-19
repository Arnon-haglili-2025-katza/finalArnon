package com.example.arnonfinalhta;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // layout עם התפריט התחתון
        setContentView(R.layout.layout_with_bottom_nav);

        // טעינת התוכן של הדף הראשי
        getLayoutInflater().inflate(
                R.layout.activity_main,
                findViewById(R.id.content_container),
                true
        );

        // סימון "בית" בתפריט
        setupBottomNav(R.id.nav_home);
    }
}
