package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huvart on 04/10/16.
 */

public class Movie implements Parcelable {

    private Long release_date;
    private String poster_path;
    private String title;
    private String backdrop_path;
    private float popularity;
    private int id;

    private boolean adult;
    private List<Integer> genre_ids;
    private String original_language;
    private String original_title;
    private String overview;
    private float vote_average;
    private int vote_count;

    public  Movie(){
        release_date = 0L;

        backdrop_path = "";
        poster_path = "";
        popularity = 0.0f;
        title = "";


        adult = false;
        genre_ids = new ArrayList<>();
        original_language = "";
        original_title = "";
        overview = "";
        vote_average = 0.0f;
        vote_count = 0;
    }

    //depricated
   /* public Movie(long releaseDate, String coverPath, String title, String backdrop, float popularity, int coverId){
        release_date = releaseDate;
        poster_path = coverPath;
        this.title = title;
        backdrop_path = backdrop;
        this.popularity = popularity;
    }*/

    public Long getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Long release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie(Parcel in) {
        release_date = in.readLong();
        poster_path = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        popularity = in.readFloat();
        id = in.readInt();

        adult = in.readByte() != 0;;
        genre_ids = in.readArrayList(null);
        original_language = in.readString();
        original_title = in.readString();
        overview = in.readString();
        vote_average = in.readFloat();
        vote_count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(release_date);
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeFloat(popularity);
        dest.writeInt(id);

        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeList(genre_ids);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeFloat(vote_average);
        dest.writeInt(vote_count);
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
