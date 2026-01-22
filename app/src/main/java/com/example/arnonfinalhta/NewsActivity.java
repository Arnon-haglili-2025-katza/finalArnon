package com.example.arnonfinalhta;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    private static final String API_KEY = "8b28a7cc55494538a188fc245645fa39";

    RecyclerView recyclerView;
    ArrayList<NewsItem> newsList;
    NewsAdapter adapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsList = new ArrayList<>();
        adapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);

        loadNews();
    }

    private void loadNews() {
        String url = "https://newsapi.org/v2/everything"
                + "?q=Hapoel Tel Aviv"
                + "&sortBy=publishedAt"
                + "&apiKey=" + API_KEY;

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

                            String title = a.optString("title");
                            String description = a.optString("description");
                            String link = a.optString("url");

                            if (title != null && link != null) {
                                newsList.add(new NewsItem(title, description, link));
                            }
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
