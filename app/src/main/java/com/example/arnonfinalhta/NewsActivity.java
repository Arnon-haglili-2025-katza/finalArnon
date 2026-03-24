package com.example.arnonfinalhta;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.recyclerview.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.*;
import java.util.*;

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

        setContentView(R.layout.layout_with_bottom_nav);
        setupBottomNav(R.id.nav_news);

        recyclerView = findViewById(R.id.recyclerNews);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        newsList = new ArrayList<>();
        adapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);

        loadNews();
    }

    private void loadNews() {

        progressBar.setVisibility(View.VISIBLE);

        String url = "https://newsapi.org/v2/everything?q=הפועל תל אביב&language=he&sortBy=publishedAt&pageSize=20&apiKey=" + API_KEY;

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

                            String title = article.optString("title");
                            if (title == null || title.equals("")) continue;

                            newsList.add(new NewsItem(
                                    title,
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
