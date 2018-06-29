package com.udacity.classroom.popularmovies.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.classroom.popularmovies.BR;
import com.udacity.classroom.popularmovies.R;
import com.udacity.classroom.popularmovies.data.MovieContract.MovieEntry;
import com.udacity.classroom.popularmovies.model.Movie;
import com.udacity.classroom.popularmovies.model.VideoAndReview;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailAdapterViewHolder> {
    private static final int VIEW_TYPE_DETAIL = 0;
    private static final int VIEW_TYPE_VIDEO = 1;
    private static final int VIEW_TYPE_REVIEW = 2;
    private final Context mContext;
    private Movie mMovie;
    private List<VideoAndReview> mVideo;
    private List<VideoAndReview> mReview;
    private View.OnClickListener mVideoClickListener;
    private View.OnClickListener mFavoriteClickListener;

    public DetailAdapter(Context mContext, Movie mMovie,
                         View.OnClickListener mVideoClickListener,
                         View.OnClickListener mFavoriteClickListener) {
        this.mContext = mContext;
        this.mMovie = mMovie;
        this.mVideoClickListener = mVideoClickListener;
        this.mFavoriteClickListener = mFavoriteClickListener;
    }

    @NonNull
    @Override
    public DetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;

        switch (viewType) {
            case VIEW_TYPE_DETAIL: {
                layoutId = R.layout.movie_detail;
                break;
            }

            case VIEW_TYPE_VIDEO: {
                layoutId = R.layout.video_list_item;
                break;
            }

            case VIEW_TYPE_REVIEW: {
                layoutId = R.layout.review_list_item;
                break;
            }

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }

        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);

        view.setFocusable(true);

        return new DetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapterViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case VIEW_TYPE_DETAIL: {
                setFavorite();
                holder.getBinding().setVariable(BR.movie, mMovie);
                holder.getBinding().setVariable(BR.favClickListener, mFavoriteClickListener);
                holder.getBinding().executePendingBindings();
                break;
            }

            case VIEW_TYPE_VIDEO: {
                holder.getBinding().setVariable(BR.video, mVideo.get(position - 1));
                holder.getBinding().setVariable(BR.vidClickListener, mVideoClickListener);
                holder.getBinding().executePendingBindings();
                break;
            }

            case VIEW_TYPE_REVIEW: {
                holder.getBinding().setVariable(BR.review, mReview.get(position - getVideoSize() - 1));
                holder.getBinding().executePendingBindings();
                break;
            }

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    @Override
    public int getItemCount() {
        return 1 + getVideoSize() + getReviewSize();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_DETAIL;
        } else if (position > 0 && position <= getVideoSize()) {
            return VIEW_TYPE_VIDEO;
        } else {
            return VIEW_TYPE_REVIEW;
        }
    }

    private int getVideoSize() {
        if (mVideo == null) {
            return 0;
        } else {
            return mVideo.size();
        }
    }

    private int getReviewSize() {
        if (mReview == null) {
            return 0;
        } else {
            return mReview.size();
        }
    }

    public class DetailAdapterViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding binding;

        public DetailAdapterViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public void setVideoData(List<VideoAndReview> videos) {
        mVideo = videos;
        notifyDataSetChanged();
    }

    public void setReviewData(List<VideoAndReview> reviews) {
        mReview = reviews;
        notifyDataSetChanged();
    }

    private void setFavorite() {
        Uri uri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, Long.valueOf(mMovie.getId()));

        Cursor cursor = mContext.getContentResolver().query(uri,
                MovieEntry.MOVIE_COLUMNS,
                null,
                null,
                null);

        if (cursor.getCount() != 0) {
            mMovie.setFavorite("true");
        } else {
            mMovie.setFavorite("false");
        }

        cursor.close();
    }
}
