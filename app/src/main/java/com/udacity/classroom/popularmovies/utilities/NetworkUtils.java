package com.udacity.classroom.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY = "Your API key here";
    private static final String THUMBNAIL_BASE_URL = "http://image.tmdb.org/t/p";

    public static URL buildUrl(String sortByPath) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(sortByPath)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
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
        Uri builtUri = Uri.parse(THUMBNAIL_BASE_URL).buildUpon()
                .appendPath(thumbnailSize)
                .appendEncodedPath(thumbnailPath)
                .build();

        return builtUri;
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
