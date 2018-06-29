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
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.udacity.classroom.popularmovies.adapter.DetailAdapter;
import com.udacity.classroom.popularmovies.databinding.ActivityDetailBinding;
import com.udacity.classroom.popularmovies.loader.AddFavoriteLoader;
import com.udacity.classroom.popularmovies.loader.MovieLoader;
import com.udacity.classroom.popularmovies.loader.RemoveFavoriteLoader;
import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.model.VideoAndReview;
import com.udacity.classroom.popularmovies.utilities.MovieJsonUtils;
import com.udacity.classroom.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    public static final String EXTRA_MESSAGE = DetailActivity.class.getSimpleName();
    private static final String DETAIL_VIDEOS = "videos";
    private static final String DETAIL_REVIEWS = "reviews";
    private static final int VIDEO_LOADER_ID = 11;
    private static final int REVIEW_LOADER_ID = 22;
    private static final int ADD_FAVORITE_LOADER_ID = 33;
    private static final int REMOVE_FAVORITE_LOADER_ID = 44;
    private Movie mMovie;
    private List<VideoAndReview> mVideoData;
    private List<VideoAndReview> mReviewData;
    private ActivityDetailBinding mDetailBinding;
    private DetailAdapter mDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();

        if (intent == null) {
            finish();
            Toast.makeText(this, R.string.unavailable_message, Toast.LENGTH_SHORT).show();
        } else {
            mMovie = intent.getExtras().getParcelable(EXTRA_MESSAGE);
            getSupportLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
            getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, null, this);
        }

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDetailBinding.detailRecyclerview.setLayoutManager(layoutManager);
        mDetailBinding.detailRecyclerview.setHasFixedSize(true);
        mDetailAdapter = new DetailAdapter(this, mMovie,
                videoClickListener, favoriteClickListener);
        mDetailBinding.detailRecyclerview.setAdapter(mDetailAdapter);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        if (id == VIDEO_LOADER_ID) {
            return new MovieLoader(this, mMovie.getId(), DETAIL_VIDEOS);
        }

        if (id == REVIEW_LOADER_ID) {
            return new MovieLoader(this, mMovie.getId(), DETAIL_REVIEWS);
        }

        if (id == ADD_FAVORITE_LOADER_ID) {
            return new AddFavoriteLoader(this, mMovie);
        }

        if (id == REMOVE_FAVORITE_LOADER_ID) {
            return new RemoveFavoriteLoader(this, mMovie);
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        try {
            if (loader.getId() == VIDEO_LOADER_ID) {
                mVideoData = MovieJsonUtils.parseDetailJson((String) data);
                mDetailAdapter.setVideoData(mVideoData);
            }

            if (loader.getId() == REVIEW_LOADER_ID) {
                mReviewData = MovieJsonUtils.parseDetailJson((String) data);
                mDetailAdapter.setReviewData(mReviewData);
            }

            if (loader.getId() == ADD_FAVORITE_LOADER_ID) {
                Uri uri = (Uri) data;
                if (uri != null) {
                    Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show();
                }
            }

            if (loader.getId() == REMOVE_FAVORITE_LOADER_ID) {
                int rowDeleted = (int) data;
                if (rowDeleted != 0) {
                    Toast.makeText(this, "Remove from favorite", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to remove", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    private View.OnClickListener videoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri youtubeUri = NetworkUtils.buildVideoUri(view.getTag().toString());

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(youtubeUri);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(DetailActivity.this,
                        "Couldn't open this video, no receiving apps installed!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener favoriteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setActivated(!view.isActivated());

            if (view.isActivated()) {
                 getSupportLoaderManager().initLoader(ADD_FAVORITE_LOADER_ID, null, DetailActivity.this);
            }

            if (!view.isActivated()) {
                getSupportLoaderManager().initLoader(REMOVE_FAVORITE_LOADER_ID, null, DetailActivity.this);
            }
        }
    };
}
