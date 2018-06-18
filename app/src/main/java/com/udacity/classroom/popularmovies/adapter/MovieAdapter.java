package com.udacity.classroom.popularmovies.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.classroom.popularmovies.R;
import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.utilities.NetworkUtils;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private List<Movie> mMovies;
    private final MovieAdapterOnClickHandler mClickHandler;
    private static final String THUMBNAIL_SIZE = "w185";

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding
                = DataBindingUtil.inflate(layoutInflater, R.layout.movie_image_item, parent, false);

        return new MovieAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        String thumbnailPath = mMovies.get(position).getThumbnail();
        Uri thumbnailUri = NetworkUtils.buildThumbnailUri(thumbnailPath, THUMBNAIL_SIZE);

        Picasso.get()
                .load(thumbnailUri)
                .placeholder(R.drawable.outline_image_black_18)
                .error(R.drawable.outline_broken_image_black_18)
                .into((ImageView) holder.binding.getRoot());
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private final ViewDataBinding binding;

        MovieAdapterViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mMovies.get(adapterPosition));
        }
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public void setMovieData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
