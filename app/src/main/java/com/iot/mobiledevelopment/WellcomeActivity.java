package com.iot.mobiledevelopment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@SuppressWarnings("ALL")
public class WellcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        findViewById(R.id.wellcome_sign_out_button).setOnClickListener(this);
        initViews();
        loadMovies();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.data_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View linearLayout = findViewById(R.id.linearLayout);
        swipeRefreshLayout = findViewById(R.id.data_list_swipe_refresh);
        doSwipeToRefresh();
    }


    private void loadMovies(){
        swipeRefreshLayout.setRefreshing(true);
        final MovieApi apiService = RetrofitClientInstance.getRetrofitInstance().create(MovieApi.class);
        final Call<List<Movie>> call = apiService.getAllMovies();


        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(final Call<List<Movie>> call,
                                   final Response<List<Movie>> response) {
                adapter = new CustomAdapter(getApplication(), response.body());
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                toastMessage("Faiure");
            }
        });
    }

    private void doSwipeToRefresh(){
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

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
