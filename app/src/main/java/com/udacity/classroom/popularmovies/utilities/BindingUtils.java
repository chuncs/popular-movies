package com.udacity.classroom.popularmovies.utilities;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingUtils {
    @BindingAdapter(value = {"imageUrl", "placeholder", "error"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String imageUrl, Drawable placeHolder, Drawable error) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(placeHolder)
                .error(error)
                .into(imageView);
    }

    @BindingAdapter({"favorite"})
    public static void setFavorite(ImageView imageView, String favorite) {
        imageView.setActivated(Boolean.valueOf(favorite));
    }
}
