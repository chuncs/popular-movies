package com.udacity.classroom.popularmovies.utilities;

import android.net.Uri;

import com.udacity.classroom.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAM = "api_key";
    private static final String THUMBNAIL_BASE_URL = "http://image.tmdb.org/t/p";
    private static final String VIDEO_BASE_URL = "https://www.youtube.com/watch";
    private static final String VIDEO_KEY_PARAM = "v";

    public static URL buildUrl(String sortByPath) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(sortByPath)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static Uri buildThumbnailUri(String thumbnailPath, String thumbnailSize) {
        return Uri.parse(THUMBNAIL_BASE_URL).buildUpon()
                .appendPath(thumbnailSize)
                .appendEncodedPath(thumbnailPath)
                .build();
    }

    public static URL buildDetailUrl(String id, String dest) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(id)
                .appendPath(dest)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static Uri buildVideoUri(String key) {
        return Uri.parse(VIDEO_BASE_URL).buildUpon()
                .appendQueryParameter(VIDEO_KEY_PARAM, key)
                .build();
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
