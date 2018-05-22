package com.udacity.classroom.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private String title;
    private String thumbnail;
    private String synopsis;
    private String rating;
    private String release_date;
    private String backdrop;

    public Movie() {
    }

    public Movie(String title, String thumbnail, String synopsis, String rating, String release_date, String backdrop) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.synopsis = synopsis;
        this.rating = rating;
        this.release_date = release_date;
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    //----------------------------------------------------------------------------------------------
    //Parcel Part

    public Movie(Parcel parcel) {
        this.title = parcel.readString();
        this.thumbnail = parcel.readString();
        this.synopsis = parcel.readString();
        this.rating = parcel.readString();
        this.release_date = parcel.readString();
        this.backdrop = parcel.readString();
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
        parcel.writeString(this.title);
        parcel.writeString(this.thumbnail);
        parcel.writeString(this.synopsis);
        parcel.writeString(this.rating);
        parcel.writeString(this.release_date);
        parcel.writeString(this.backdrop);
    }
}
