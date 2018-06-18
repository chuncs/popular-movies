package com.udacity.classroom.popularmovies.utilities;

import android.util.Pair;

import com.udacity.classroom.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtils {
    private final static String MOVIE_ID = "id";
    private final static String MOVIE_TITLE = "title";
    private final static String MOVIE_THUMBNAIL = "poster_path";
    private final static String MOVIE_SYNOPSIS = "overview";
    private final static String MOVIE_RATING = "vote_average";
    private final static String MOVIE_RELEASE_DATE = "release_date";
    private final static String JSON_RESULT = "results";
    private final static String MOVIE_BACKDROP = "backdrop_path";
    private final static String VIDEO_KEY = "key";
    private final static String VIDEO_NAME = "name";
    private final static String VIDEO_TYPE = "type";
    private final static String VIDEO_TYPE_TRAILER = "Trailer";
    private final static String REVIEW_AUTHOR = "author";
    private final static String REVIEW_CONTENT = "content";

    public static List<Movie> parseMovieJson(String movieJsonStr) throws JSONException {
        List<Movie> parseMovieData = new ArrayList<>();
        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray resultArray = movieJson.optJSONArray(JSON_RESULT);

        for (int i = 0; i < resultArray.length(); i++) {
            Movie movieInfo = new Movie();
            JSONObject result = resultArray.optJSONObject(i);

            String id = result.optString(MOVIE_ID);
            movieInfo.setId(id);

            String title = result.optString(MOVIE_TITLE);
            movieInfo.setTitle(title);

            String thumbnail = result.optString(MOVIE_THUMBNAIL);
            movieInfo.setThumbnail(thumbnail);

            String synopsis = result.optString(MOVIE_SYNOPSIS);
            movieInfo.setSynopsis(synopsis);

            String rating = result.optString(MOVIE_RATING);
            movieInfo.setRating(rating);

            String release_date = result.optString(MOVIE_RELEASE_DATE);
            movieInfo.setRelease_date(release_date);

            String backdrop = result.optString(MOVIE_BACKDROP);
            movieInfo.setBackdrop(backdrop);

            parseMovieData.add(movieInfo);
        }

        return parseMovieData;
    }

    public static List<Pair<String, String>> parseDetailJson(String detailJsonStr) throws JSONException {
        List<Pair<String, String>> parseDetailData = new ArrayList<>();
        JSONObject detailJson = new JSONObject(detailJsonStr);
        JSONArray resultArray = detailJson.optJSONArray(JSON_RESULT);

        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject result = resultArray.optJSONObject(i);

            String type = result.optString(VIDEO_TYPE);

            if (!type.isEmpty()) {
                if (type.equals(VIDEO_TYPE_TRAILER)) {
                    String key = result.optString(VIDEO_KEY);
                    String name = result.optString(VIDEO_NAME);

                    parseDetailData.add(Pair.create(key, name));
                }
            } else {
                String author = result.optString(REVIEW_AUTHOR);
                String content = result.optString(REVIEW_CONTENT);

                parseDetailData.add(Pair.create(author, content));
            }
        }

        return parseDetailData;
    }
}
