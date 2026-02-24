package com.example.arnonfinalhta;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends BaseActivity {

    private static final String API_KEY = "8b28a7cc55494538a188fc245645fa39";

    RecyclerView recyclerView;
    ProgressBar progressBar;

    ArrayList<NewsItem> newsList;
    NewsAdapter adapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // הלייאאוט הראשי עם הבוטום נאב
        setContentView(R.layout.layout_with_bottom_nav);

        FrameLayout frame = findViewById(R.id.content_frame);

        // טוען את הלייאאוט של החדשות
        View view = getLayoutInflater().inflate(R.layout.activity_news, frame, false);
        frame.addView(view);

        setupBottomNav(R.id.nav_news);

        recyclerView = view.findViewById(R.id.recyclerNews);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsList = new ArrayList<>();
        adapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);

        loadNews();
    }

    private void loadNews() {

        progressBar.setVisibility(View.VISIBLE);

        String url =
                "https://newsapi.org/v2/everything?q=הפועל תל אביב&language=he&sortBy=publishedAt&apiKey="
                        + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                response -> {

                    progressBar.setVisibility(View.GONE);

                    try {

                        JSONArray articles = response.getJSONArray("articles");
                        newsList.clear();

                        for (int i = 0; i < articles.length(); i++) {

                            JSONObject article = articles.getJSONObject(i);

                            newsList.add(new NewsItem(
                                    article.optString("title"),
                                    article.optString("description"),
                                    article.optString("url"),
                                    article.optString("urlToImage")

                            ));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                },

                error -> {
                    progressBar.setVisibility(View.GONE);
                    error.printStackTrace();
                }
        );

        queue.add(request);
    }
}
