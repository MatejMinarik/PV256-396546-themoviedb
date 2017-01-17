package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huvart on 23/10/2016.
 */

public class Genre implements Parcelable{
    private List<Movie> mMovieList;
    private String name;
    private int id;

    public  Genre(){
        name = "none";
        id = 0;
        mMovieList = new ArrayList<>();
    }

    public Genre(String ganreName, int genereId, List<Movie> movieList){
        name = ganreName;
        mMovieList = movieList;
        id = genereId;
    }

    public List<Movie> getMovieList(){
        return mMovieList;
    }

    public void setMovieList(List<Movie> movieList){
        mMovieList = movieList;
    }

    public String getGanreName(){
        return name;
    }

    public void setGanreName(String ganreName){
        name = ganreName;
    }

    public int getGenereId() {
        return id;
    }

    public void setGenereId(int genereId) {
        id = genereId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(mMovieList);
        dest.writeInt(id);
    }

    public Genre(Parcel in) {
        name = in.readString();
        mMovieList = in.readArrayList(null);
        id = in.readInt();
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
