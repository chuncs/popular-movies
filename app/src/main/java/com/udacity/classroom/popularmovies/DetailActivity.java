package com.udacity.classroom.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.utilities.NetworkUtils;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = DetailActivity.class.getSimpleName();
    private static final String BACKDROP_SIZE = "w780";
    private static final String POSTER_SIZE = "w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            Toast.makeText(this, "Movie data not available.", Toast.LENGTH_SHORT).show();
        }

        Movie movie = intent.getExtras().getParcelable(EXTRA_MESSAGE);
        populateUI(movie);
    }

    private void populateUI(Movie movie) {
        ImageView backdropIv = findViewById(R.id.backdrop_iv);
        ImageView posterIv = findViewById(R.id.poster_iv);
        TextView titleTv = findViewById(R.id.title_tv);
        TextView userRatingTv = findViewById(R.id.user_rating_tv);
        TextView releaseDateTv = findViewById(R.id.release_date_tv);
        TextView overviewTv = findViewById(R.id.overview_tv);

        String backdropPath = movie.getBackdrop();
        String posterPath = movie.getThumbnail();

        Uri backdropUri = NetworkUtils.buildThumbnailUri(backdropPath, BACKDROP_SIZE);
        Uri posterUri = NetworkUtils.buildThumbnailUri(posterPath, POSTER_SIZE);

        Picasso.get()
                .load(backdropUri)
                .placeholder(R.drawable.outline_image_black_18)
                .error(R.drawable.outline_broken_image_black_18)
                .into(backdropIv);
        Picasso.get()
                .load(posterUri)
                .placeholder(R.drawable.outline_image_black_18)
                .error(R.drawable.outline_broken_image_black_18)
                .into(posterIv);

        setTitle(movie.getTitle());
        titleTv.setText(movie.getTitle());
        userRatingTv.setText(movie.getRating() + "/10");
        releaseDateTv.setText(movie.getRelease_date());
        overviewTv.setText(movie.getSynopsis());
    }
}
