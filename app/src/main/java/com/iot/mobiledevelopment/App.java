package com.iot.mobiledevelopment;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private MovieApi movieService;
    private FirebaseAuth auth;

    public void onCreate()
    {
        super.onCreate();
        auth = FirebaseAuth.getInstance();
        movieService = createMovieApiService();
    }

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
}
