package com.udacity.classroom.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.classroom.popularmovies.adapter.VideoAdapter;
import com.udacity.classroom.popularmovies.databinding.MovieDetailBinding;
import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.utilities.MovieJsonUtils;
import com.udacity.classroom.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final String EXTRA_MESSAGE = DetailActivity.class.getSimpleName();
    private static final String BACKDROP_SIZE = "w780";
    private static final String POSTER_SIZE = "w185";
    private static final String DETAIL_VIDEOS = "videos";
    private static final String DETAIL_REVIEWS = "reviews";
    private static final int VIDEO_LOADER_ID = 35;
    private static final int REVIEW_LOADER_ID = 56;
    private Movie mMovie;
    List<Pair<String, String>> mVideoData;
    List<Pair<String, String>> mReviewData;
    MovieDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail);

        Intent intent = getIntent();

        if (intent == null) {
            finish();
            Toast.makeText(this, R.string.unavailable_message, Toast.LENGTH_SHORT).show();
        } else {
            mMovie = intent.getExtras().getParcelable(EXTRA_MESSAGE);
            getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
            getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, null, this);
            populateUI(mMovie);
        }
    }

    private void populateUI(Movie movie) {
        String backdropPath = movie.getBackdrop();
        String posterPath = movie.getThumbnail();

        Uri backdropUri = NetworkUtils.buildThumbnailUri(backdropPath, BACKDROP_SIZE);
        Uri posterUri = NetworkUtils.buildThumbnailUri(posterPath, POSTER_SIZE);

        Picasso.get()
                .load(backdropUri)
                .placeholder(R.drawable.outline_image_black_18)
                .error(R.drawable.outline_broken_image_black_18)
                .into(mDetailBinding.backdropIv);
        Picasso.get()
                .load(posterUri)
                .placeholder(R.drawable.outline_image_black_18)
                .error(R.drawable.outline_broken_image_black_18)
                .into(mDetailBinding.posterIv);

        setTitle(movie.getTitle());
        mDetailBinding.titleTv.setText(movie.getTitle());
        mDetailBinding.userRatingTv.setText(movie.getRating().concat(getString(R.string.full_score)));
        mDetailBinding.releaseDateTv.setText(movie.getRelease_date());
        mDetailBinding.overviewTv.setText(movie.getSynopsis());

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == VIDEO_LOADER_ID) {
            return new MovieLoader(this, mMovie.getId(), DETAIL_VIDEOS);
        }

        if (id == REVIEW_LOADER_ID) {
            return new MovieLoader(this, mMovie.getId(), DETAIL_REVIEWS);
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            if (loader.getId() == VIDEO_LOADER_ID) {
                mVideoData = MovieJsonUtils.parseDetailJson(data);
                mDetailBinding.videoLv.setAdapter(new VideoAdapter(this, mVideoData));
            }

            if (loader.getId() == REVIEW_LOADER_ID) {
                mReviewData = MovieJsonUtils.parseDetailJson(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
