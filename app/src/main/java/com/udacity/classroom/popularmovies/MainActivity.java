package com.udacity.classroom.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.udacity.classroom.popularmovies.adapter.MovieAdapter;
import com.udacity.classroom.popularmovies.databinding.ActivityMainBinding;
import com.udacity.classroom.popularmovies.loader.FavoriteMovieLoader;
import com.udacity.classroom.popularmovies.loader.MovieLoader;
import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.utilities.MovieJsonUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks {
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String BUNDLE_KEY = MainActivity.class.getSimpleName();
    private static final int NUMBER_COLUMNS = 2;
    private static final int MOVIE_LOADER_ID = 23;
    private static final int FAVORITE_LOADER_ID = 25;
    private static String mCurrentPath = POPULAR;
    private ArrayList<Movie> mMovie;
    private MovieAdapter mMovieAdapter;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, NUMBER_COLUMNS);
        mBinding.movieRecyclerview.setLayoutManager(layoutManager);
        mBinding.movieRecyclerview.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mBinding.movieRecyclerview.setAdapter(mMovieAdapter);

        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelableArrayList(BUNDLE_KEY);
            mMovieAdapter.setMovieData(mMovie);
        } else {
            getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
        }
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MESSAGE, movie);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        mBinding.loadingIndicatorPb.setVisibility(View.VISIBLE);

        if (id == MOVIE_LOADER_ID) {
            return new MovieLoader(this, null, mCurrentPath);
        }

        if (id == FAVORITE_LOADER_ID) {
            return new FavoriteMovieLoader(this);
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        mBinding.loadingIndicatorPb.setVisibility(View.INVISIBLE);

        if (loader.getId() == MOVIE_LOADER_ID) {
            List<Movie> jsonMovieData = null;

            try {
                jsonMovieData = MovieJsonUtils.parseMovieJson((String) data);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMovie = (ArrayList<Movie>) jsonMovieData;
        }

        if (loader.getId() == FAVORITE_LOADER_ID) {
            mMovie = (ArrayList<Movie>) data;
        }

        mMovieAdapter.setMovieData(mMovie);

        if (mMovie != null) {
            showMovieThumbnail();
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    private void showMovieThumbnail() {
        mBinding.errorMessageTv.setVisibility(View.INVISIBLE);
        mBinding.movieRecyclerview.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mBinding.movieRecyclerview.setVisibility(View.INVISIBLE);
        mBinding.errorMessageTv.setVisibility(View.VISIBLE);
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
            mCurrentPath = POPULAR;
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
            return true;
        }

        if (id == R.id.top_rated) {
            mCurrentPath = TOP_RATED;
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
            return true;
        }

        if (id == R.id.favorite) {
            getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
