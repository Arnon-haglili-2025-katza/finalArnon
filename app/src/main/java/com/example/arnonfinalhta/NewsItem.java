package com.example.arnonfinalhta;

public class NewsItem {

    String title;
    String description;
    String url;
    String imageUrl;
    String source;
    String date;

    public NewsItem(String title, String description, String url,
                    String imageUrl, String source, String date) {

        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.source = source;
        this.date = date;
    }
}
