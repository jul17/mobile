package com.iot.mobiledevelopment;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private List<Movie> mMovies;
    private MovieApi movieService;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public void onCreate()
    {
        super.onCreate();
        auth = FirebaseAuth.getInstance();
        movieService = createMovieApiService();
        loadMovies();
    }

    public FirebaseUser getUser() { return user; }

    public FirebaseAuth getAuth(){
        return auth;
    }

    public MovieApi getMovieService(){
        return movieService;
    }

    public MovieApi createMovieApiService(){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-mobile-development-49211.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MovieApi.class);
    }

    private void loadMovies(){
        final MovieApi apiService = getMovieService();
        final Call<List<Movie>> call = apiService.getAllMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(final Call<List<Movie>> call,
                                   final Response<List<Movie>> response) {
                mMovies = response.body();
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                mMovies = new ArrayList<>(0);
            }
        });
    }
}
