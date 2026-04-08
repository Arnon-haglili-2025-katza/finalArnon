package com.example.arnonfinalhta;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsActivity extends BaseActivity {

    private static final String API_KEY = "d1f970b51416fddea8256f319d35b231";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;

    private ArrayList<NewsItem> newsList;
    private NewsAdapter adapter;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setupBottomNav(R.id.nav_news);

        recyclerView = findViewById(R.id.recyclerNews);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        newsList = new ArrayList<>();
        adapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);

        swipeRefresh.setOnRefreshListener(this::loadNews);

        loadNews();
    }

    private void loadNews() {

        swipeRefresh.setRefreshing(true);

        String url = "https://gnews.io/api/v4/search?q=Hapoel Tel Aviv&lang=en&max=10&apikey=" + API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                response -> {

                    swipeRefresh.setRefreshing(false);

                    try {

                        JSONArray articles = response.getJSONArray("articles");

                        newsList.clear();

                        for (int i = 0; i < articles.length(); i++) {

                            JSONObject article = articles.getJSONObject(i);

                            String title = article.optString("title", "");
                            String description = article.optString("description", "");
                            String urlArticle = article.optString("url", "");
                            String imageUrl = article.optString("image", "");

                            newsList.add(new NewsItem(
                                    title,
                                    description,
                                    urlArticle,
                                    imageUrl
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },

                error -> swipeRefresh.setRefreshing(false)
        );

        queue.add(request);
    }
}