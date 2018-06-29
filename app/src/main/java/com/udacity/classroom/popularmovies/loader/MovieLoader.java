package com.udacity.classroom.popularmovies.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.udacity.classroom.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MovieLoader extends AsyncTaskLoader<String> {
    private String mId;
    private String mPath;
    private String mMovieData;

    public MovieLoader(Context context, String id, String path) {
        super(context);
        mId = id;
        mPath = path;
    }

    @Override
    protected void onStartLoading() {
        if (mMovieData != null) {
            deliverResult(mMovieData);
        } else {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        URL movieRequestUrl;

        if (mId == null) {
            movieRequestUrl = NetworkUtils.buildUrl(mPath);
        } else {
            movieRequestUrl = NetworkUtils.buildDetailUrl(mId, mPath);
        }

        try {
            return NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(String data) {
        mMovieData = data;
        super.deliverResult(data);
    }
}
