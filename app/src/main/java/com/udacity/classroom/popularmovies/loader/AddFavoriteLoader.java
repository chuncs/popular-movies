package com.udacity.classroom.popularmovies.loader;

import android.support.v4.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.udacity.classroom.popularmovies.data.MovieContract.MovieEntry;
import com.udacity.classroom.popularmovies.model.Movie;

public class AddFavoriteLoader extends AsyncTaskLoader<Uri> {
    private Context mContext;
    private Movie mMovieData;
    private Uri mUri;

    public AddFavoriteLoader(Context context, Movie mMovieData) {
        super(context);
        this.mContext = context;
        this.mMovieData = mMovieData;
    }

    @Override
    protected void onStartLoading() {
        if (mUri != null) {
            deliverResult(mUri);
        } else {
            forceLoad();
        }
    }

    @Override
    public Uri loadInBackground() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieEntry.COLUMN_MOVIE_ID, Integer.valueOf(mMovieData.getId()));
        contentValues.put(MovieEntry.COLUMN_TITLE, mMovieData.getTitle());
        contentValues.put(MovieEntry.COLUMN_THUMBNAIL_URL, mMovieData.getThumbnailUrl());
        contentValues.put(MovieEntry.COLUMN_BACKDROP_URL, mMovieData.getBackdropUrl());
        contentValues.put(MovieEntry.COLUMN_SYNOPSIS, mMovieData.getSynopsis());
        contentValues.put(MovieEntry.COLUMN_RATING, mMovieData.getRating());
        contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, mMovieData.getRelease_date());

        return mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void deliverResult(Uri data) {
        mUri = data;
        super.deliverResult(data);
    }
}
