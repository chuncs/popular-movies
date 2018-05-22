package com.udacity.classroom.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.utilities.MovieJsonUtils;
import com.udacity.classroom.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String DEFAULT_MENU = POPULAR;
    private static final String BUNDLE_KEY = MainActivity.class.getSimpleName();
    private GridView mGridView;
    private TextView mErrorMessage;
    private ProgressBar mLoadingIndicator;
    private ArrayList<Movie> mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.movie_grid_view);
        mErrorMessage = findViewById(R.id.error_message_tv);
        mLoadingIndicator = findViewById(R.id.loading_indicator_pb);

        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelableArrayList(BUNDLE_KEY);
            loadMovieData(mMovie);
        } else {
            new FetchMovieTask().execute(DEFAULT_MENU);
        }
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... paths) {
            if (paths.length == 0) {
                return null;
            }

            String path = paths[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(path);
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                List<Movie> jsonMovieData = MovieJsonUtils.parseMovieJson(jsonMovieResponse);

                return jsonMovieData;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movies != null) {
                mMovie = (ArrayList<Movie>) movies;
                loadMovieData(mMovie);
            } else {
                showErrorMessage();
            }
        }
    }

    private void loadMovieData(final ArrayList<Movie> movies) {
        showMovieThumbnail();
        mGridView.setAdapter(new MovieAdapter(MainActivity.this, movies));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchMovieDetailActivity(movies.get(position));
            }
        });
    }

    private void showMovieThumbnail() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mGridView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mGridView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void launchMovieDetailActivity(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MESSAGE, movie);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_KEY, mMovie);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.popular) {
            new FetchMovieTask().execute(POPULAR);
            return true;
        }

        if (id == R.id.top_rated) {
            new FetchMovieTask().execute(TOP_RATED);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
