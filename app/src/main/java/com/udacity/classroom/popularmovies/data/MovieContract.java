package com.udacity.classroom.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.udacity.classroom.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_BACKDROP_URL = "backdrop_url";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String[] MOVIE_COLUMNS = {
                COLUMN_MOVIE_ID,
                COLUMN_TITLE,
                COLUMN_THUMBNAIL_URL,
                COLUMN_BACKDROP_URL,
                COLUMN_SYNOPSIS,
                COLUMN_RATING,
                COLUMN_RELEASE_DATE
        };

        public static final int INDEX_MOVIE_ID = 0;
        public static final int INDEX_MOVIE_TITLE = 1;
        public static final int INDEX_MOVIE_THUMBNAIL_URL = 2;
        public static final int INDEX_MOVIE_BACKDROP_URL = 3;
        public static final int INDEX_MOVIE_SYNOPSIS = 4;
        public static final int INDEX_MOVIE_RATING = 5;
        public static final int INDEX_MOVIE_RELEASE_DATE = 6;
    }
}
