package com.udacity.classroom.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private String id;
    private String title;
    private String synopsis;
    private String rating;
    private String release_date;
    private String thumbnailUrl;
    private String backdropUrl;
    private String favorite;

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    //----------------------------------------------------------------------------------------------
    //Parcel Part

    private Movie(Parcel parcel) {
        this.id = parcel.readString();
        this.title = parcel.readString();
        this.synopsis = parcel.readString();
        this.rating = parcel.readString();
        this.release_date = parcel.readString();
        this.thumbnailUrl = parcel.readString();
        this.backdropUrl = parcel.readString();
        this.favorite = parcel.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.synopsis);
        parcel.writeString(this.rating);
        parcel.writeString(this.release_date);
        parcel.writeString(this.thumbnailUrl);
        parcel.writeString(this.backdropUrl);
        parcel.writeString(this.favorite);
    }
}
