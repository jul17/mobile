package com.iot.mobiledevelopment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class WellcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        findViewById(R.id.wellcome_sign_out_button).setOnClickListener(this);
        initViews();
        loadMovies();
        registerNetworkMonitoring();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.welcome_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View linearLayout = findViewById(R.id.linearLayout);
        swipeRefreshLayout = findViewById(R.id.welcome_swipe_refresh);
        setupSwipeToRefresh();
    }


    private void loadMovies(){
        swipeRefreshLayout.setRefreshing(true);
        final MovieApi apiService = getApplicationEx().getMovieService();
        final Call<List<Movie>> call = apiService.getAllMovies();


        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(final Call<List<Movie>> call,
                                   final Response<List<Movie>> response) {
                adapter = new CustomAdapter(response.body());
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Snackbar.make(linearLayout, R.string.failure, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupSwipeToRefresh(){
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadMovies();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void registerNetworkMonitoring() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkChangeReceiver receiver = new NetworkChangeReceiver(linearLayout);
        this.registerReceiver(receiver, filter);
    }

    private void signOut() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wellcome_sign_out_button:
                signOut();
                break;
        }
    }

    private App getApplicationEx(){
        return ((App) getApplication());
    }
}
