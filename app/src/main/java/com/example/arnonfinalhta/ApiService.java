package com.example.arnonfinalhta;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {

    @GET("v2/everything?q=הפועל תל אביב&language=he&apiKey=8b28a7cc55494538a188fc245645fa39")
    Call<NewsResponse> getHapoelNews();
}


