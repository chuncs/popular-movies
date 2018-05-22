package com.udacity.classroom.popularmovies.utilities;

import com.udacity.classroom.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtils {
    private final static String MOVIE_TITLE = "title";
    private final static String MOVIE_THUMBNAIL = "poster_path";
    private final static String MOVIE_SYNOPSIS = "overview";
    private final static String MOVIE_RATING = "vote_average";
    private final static String MOVIE_RELEASE_DATE = "release_date";
    private final static String MOVIE_RESULT = "results";
    private final static String MOVIE_BACKDROP = "backdrop_path";

    public static List<Movie> parseMovieJson(String movieJsonStr) throws JSONException {
        List<Movie> parseMovieData = new ArrayList<>();
        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray resultArray = movieJson.optJSONArray(MOVIE_RESULT);

        for (int i = 0; i < resultArray.length(); i++) {
            Movie movieInfo = new Movie();
            JSONObject result = resultArray.optJSONObject(i);

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
}
