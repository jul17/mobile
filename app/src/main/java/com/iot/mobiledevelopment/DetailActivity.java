package com.iot.mobiledevelopment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.iot.mobiledevelopment.MoviesFragment.EXTRA_DESCRIPTION;
import static com.iot.mobiledevelopment.MoviesFragment.EXTRA_TITTLE;
import static com.iot.mobiledevelopment.MoviesFragment.EXTRA_URL;
import static com.iot.mobiledevelopment.MoviesFragment.EXTRA_YEAR;
import static com.iot.mobiledevelopment.NotificationService.EXTRA_COMPARE_TITLE;

public class DetailActivity extends AppCompatActivity {

    ImageView posterImageView;
    TextView titleTextView;
    TextView yearTextView;
    TextView descriptionTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITTLE);
        Integer year = intent.getIntExtra(EXTRA_YEAR, 0);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        String title_compare = intent.getStringExtra(EXTRA_COMPARE_TITLE);

         posterImageView = findViewById(R.id.detail_imageView);
         titleTextView = findViewById(R.id.detail_tittle);
         yearTextView = findViewById(R.id.detail_year);
         descriptionTextView = findViewById(R.id.detail_description);

         getData(title_compare);

        Picasso.get().load(imageUrl).fit().centerInside().into(posterImageView);
        titleTextView.setText(title);
        yearTextView.setText("Year: " + year.toString());
        descriptionTextView.setText(description);

    }

    private void getData(final String title_compare) {
        final MovieApi api = getApplicationEx().getMovieService();
        final Call<List<Movie>> call = api.getAllMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(final Call<List<Movie>> call,
                                   final Response<List<Movie>> response) {
                CustomAdapter adapter = new CustomAdapter(response.body());
                for (int i = 0; adapter.getMovieList().size() > i; i++) {
                    if (adapter.getMovieList().get(i).getTitle().equals(title_compare)) {
                        Picasso.get().load(adapter.getMovieList().get(i).getPoster()).into(posterImageView);
                        titleTextView.setText(adapter.getMovieList().get(i).getTitle());
                        descriptionTextView.setText(adapter.getMovieList().get(i).getDescription());
                        yearTextView.setText(adapter.getMovieList().get(i).getYear());
                    }
                }
                if (title_compare == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.failure),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
            }
        });
    }

    private App getApplicationEx(){
        return ((App) getApplication());
    }
}
