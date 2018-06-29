package com.udacity.classroom.popularmovies.utilities;

import android.net.Uri;

import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.model.VideoAndReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtils {
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_THUMBNAIL = "poster_path";
    private static final String MOVIE_SYNOPSIS = "overview";
    private static final String MOVIE_RATING = "vote_average";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String JSON_RESULT = "results";
    private static final String MOVIE_BACKDROP = "backdrop_path";
    private static final String VIDEO_KEY = "key";
    private static final String VIDEO_NAME = "name";
    private static final String VIDEO_TYPE = "type";
    private static final String VIDEO_TYPE_TRAILER = "Trailer";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";
    private static final String THUMBNAIL_SIZE = "w185";
    private static final String BACKDROP_SIZE = "w780";

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
            Uri thumbnailUri = NetworkUtils.buildThumbnailUri(thumbnail, THUMBNAIL_SIZE);
            movieInfo.setThumbnailUrl(thumbnailUri.toString());

            String synopsis = result.optString(MOVIE_SYNOPSIS);
            movieInfo.setSynopsis(synopsis);

            String rating = result.optString(MOVIE_RATING);
            movieInfo.setRating(rating);

            String release_date = result.optString(MOVIE_RELEASE_DATE);
            movieInfo.setRelease_date(release_date);

            String backdrop = result.optString(MOVIE_BACKDROP);
            Uri backdropUri = NetworkUtils.buildThumbnailUri(backdrop, BACKDROP_SIZE);
            movieInfo.setBackdropUrl(backdropUri.toString());

            parseMovieData.add(movieInfo);
        }

        return parseMovieData;
    }

    public static List<VideoAndReview> parseDetailJson(String detailJsonStr) throws JSONException {
        List<VideoAndReview> parseDetailData = new ArrayList<>();
        JSONObject detailJson = new JSONObject(detailJsonStr);
        JSONArray resultArray = detailJson.optJSONArray(JSON_RESULT);

        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject result = resultArray.optJSONObject(i);

            String type = result.optString(VIDEO_TYPE);

            if (!type.isEmpty()) {
                if (type.equals(VIDEO_TYPE_TRAILER)) {
                    String key = result.optString(VIDEO_KEY);
                    String name = result.optString(VIDEO_NAME);
                    VideoAndReview video = new VideoAndReview();

                    video.setKey(key);
                    video.setName(name);
                    parseDetailData.add(video);
                }
            } else {
                String author = result.optString(REVIEW_AUTHOR);
                String content = result.optString(REVIEW_CONTENT);
                VideoAndReview review = new VideoAndReview();

                review.setAuthor(author);
                review.setContent(content);
                parseDetailData.add(review);
            }
        }

        return parseDetailData;
    }
}
