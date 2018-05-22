package com.udacity.classroom.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class MovieAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Movie> mMovies;
    private static final String THUMBNAIL_SIZE = "w185";

    public MovieAdapter(@NonNull Context context, List<Movie> movies) {
        super(context, R.layout.gridview_item_image, movies);
        mContext = context;
        mMovies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.gridview_item_image, parent, false);
        }

        String thumbnailPath = mMovies.get(position).getThumbnail();
        Uri thumbnailUri = NetworkUtils.buildThumbnailUri(thumbnailPath, THUMBNAIL_SIZE);

        Picasso.get()
                .load(thumbnailUri)
                .placeholder(R.drawable.outline_image_black_18)
                .error(R.drawable.outline_broken_image_black_18)
                .into((ImageView) convertView);

        return convertView;
    }
}
