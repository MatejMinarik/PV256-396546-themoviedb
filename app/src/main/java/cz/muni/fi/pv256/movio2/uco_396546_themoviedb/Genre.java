package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huvart on 23/10/2016.
 */

public class Genre implements Parcelable{
    private List<Movie> mMovieList;
    private String mGanreName;

    public Genre(String ganreName, List<Movie> movieList){
        mGanreName = ganreName;
        mMovieList = movieList;
    }

    List<Movie> getMovieList(){
        return mMovieList;
    }

    void setMovieList(List<Movie> movieList){
        mMovieList = movieList;
    }

    String getGanreName(){
        return mGanreName;
    }

    void setGanreName(String ganreName){
        mGanreName = ganreName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGanreName);
        dest.writeList(mMovieList);
    }

    public Genre(Parcel in) {
        mGanreName = in.readString();
        mMovieList = in.readArrayList(null);
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
