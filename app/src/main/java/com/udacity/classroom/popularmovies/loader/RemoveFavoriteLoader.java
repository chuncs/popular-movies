package com.udacity.classroom.popularmovies.loader;

import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import com.udacity.classroom.popularmovies.data.MovieContract.MovieEntry;
import com.udacity.classroom.popularmovies.model.Movie;

public class RemoveFavoriteLoader extends AsyncTaskLoader<Integer> {
    private Context mContext;
    private Movie mMovieData;
    private int mRowDeleted;

    public RemoveFavoriteLoader(Context context, Movie mMovieData) {
        super(context);
        this.mContext = context;
        this.mMovieData = mMovieData;
    }

    @Override
    protected void onStartLoading() {
        if (mRowDeleted != 0) {
            deliverResult(mRowDeleted);
        } else {
            forceLoad();
        }
    }

    @Override
    public Integer loadInBackground() {
        Long id = Long.valueOf(mMovieData.getId());
        Uri uri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, id);

        return mContext.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void deliverResult(@Nullable Integer data) {
        mRowDeleted = data;
        super.deliverResult(data);
    }
}
