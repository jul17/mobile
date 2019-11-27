package com.iot.mobiledevelopment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {

    @GET("/custom_movies/")
    Call<List<Movie>> getAllMovies();
}
