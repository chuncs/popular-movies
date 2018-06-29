package com.udacity.classroom.popularmovies.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.udacity.classroom.popularmovies.data.MovieContract.MovieEntry;
import com.udacity.classroom.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieLoader extends AsyncTaskLoader<List<Movie>> {
    private Context mContext;
    private List<Movie> mMovieData = new ArrayList<>();

    public FavoriteMovieLoader(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onStartLoading() {
        if (!mMovieData.isEmpty()) {
            deliverResult(mMovieData);
        } else {
            forceLoad();
        }
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI,
                MovieEntry.MOVIE_COLUMNS,
                null,
                null,
                null);

        try {
            while (cursor.moveToNext()) {
                Movie movie = new Movie();
                movie.setId(String.valueOf(cursor.getInt(MovieEntry.INDEX_MOVIE_ID)));
                movie.setTitle(cursor.getString(MovieEntry.INDEX_MOVIE_TITLE));
                movie.setThumbnailUrl(cursor.getString(MovieEntry.INDEX_MOVIE_THUMBNAIL_URL));
                movie.setBackdropUrl(cursor.getString(MovieEntry.INDEX_MOVIE_BACKDROP_URL));
                movie.setSynopsis(cursor.getString(MovieEntry.INDEX_MOVIE_SYNOPSIS));
                movie.setRating(cursor.getString(MovieEntry.INDEX_MOVIE_RATING));
                movie.setRelease_date(cursor.getString(MovieEntry.INDEX_MOVIE_RELEASE_DATE));
                mMovieData.add(movie);
            }
        } finally {
            cursor.close();
        }

        return mMovieData;
    }

    @Override
    public void deliverResult(@Nullable List<Movie> data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
