package com.example.arnonfinalhta;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.*;
import java.util.*;

public class NewsActivity extends BaseActivity {

    private static final String API_KEY = "שים_כאן_את_המפתח";

    RecyclerView recyclerView;
    ArrayList<NewsItem> newsList;
    NewsAdapter adapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_with_bottom_nav);

        FrameLayout frame = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_news, frame, true);

        setupBottomNav(R.id.nav_news);

        recyclerView = findViewById(R.id.recyclerNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsList = new ArrayList<>();
        adapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);

        loadNews();
    }

    private void loadNews() {

        String url = "https://newsapi.org/v2/everything?q=הפועל תל אביב&language=he&apiKey=" + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {

                    try {

                        JSONArray articles = response.getJSONArray("articles");
                        newsList.clear();

                        for (int i = 0; i < articles.length(); i++) {

                            JSONObject a = articles.getJSONObject(i);

                            newsList.add(new NewsItem(
                                    a.optString("title"),
                                    a.optString("description"),
                                    a.optString("url")
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },
                error -> error.printStackTrace()
        );

        queue.add(request);
    }
}
