package com.iot.mobiledevelopment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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

         //getData(title_compare);

        Picasso.get().load(imageUrl).fit().centerInside().into(posterImageView);
        titleTextView.setText(title);
        yearTextView.setText("Year: " + year.toString());
        descriptionTextView.setText(description);

    }
}
