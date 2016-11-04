package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Huvart on 04/10/16.
 */

public class Movie implements Parcelable {

    private long mReleaseDate;
    private String poster_path;
    private String title;
    private String backdrop_path;
    private float popularity;
    private int id;
    //temporary
    public int mCoverId;

    public  Movie(){
        mReleaseDate = 0;
        mCoverId = R.drawable.stolety_starik;
        backdrop_path = "";
        poster_path = "";
        popularity = 0.0f;
        title = "";
    }

    public Movie(long releaseDate, String coverPath, String title, String backdrop, float popularity, int coverId){
        mReleaseDate = releaseDate;
        poster_path = coverPath;
        title = title;
        backdrop_path = backdrop;
        popularity = popularity;
        mCoverId = coverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getCoverPath() {
        return poster_path;
    }

    public void setCoverPath(String coverPath) {
        poster_path = coverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

    public String getBackdrop() {
        return backdrop_path;
    }

    public void setBackdrop(String backdrop) {
        backdrop_path = backdrop;
    }

    public float getPopularity(){
        return popularity;
    }

    public void setPopularity(float popularity){
        popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(Parcel in) {
        mReleaseDate = in.readLong();
        poster_path = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readFloat();
        mCoverId = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mReleaseDate);
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeFloat(popularity);
        dest.writeInt(mCoverId);
        dest.writeInt(id);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
