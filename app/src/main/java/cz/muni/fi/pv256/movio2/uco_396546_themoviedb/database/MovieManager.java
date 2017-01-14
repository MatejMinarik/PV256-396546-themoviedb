package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieContract;

/**
 * Created by Huvart on 08/01/2017.
 */

public class MovieManager {

    public static final int COL_MOVIE_ID = 0;
    public static final int COL_MOVIE_RELEASE_DATE = 1;
    public static final int COL_MOVIE_POSTER_PATH = 2;
    public static final int COL_MOVIE_TITLE_TEXT = 3;
    public static final int COL_MOVIE_BACKDROP_PATH = 4;
    public static final int COL_MOVIE_POPULARITY = 5;
    public static final int COL_MOVIE_ADULT = 6;
    public static final int COL_MOVIE_GENERES_IDS = 7;
    public static final int COL_MOVIE_ORIGINAL_LANGUAGE = 8;
    public static final int COL_MOVIE_ORIGINAL_TITLE = 9;
    public static final int COL_MOVIE_OVERVIEW = 10;
    public static final int COL_MOVIE_VOTE_AVERAGE = 11;
    public static final int COL_MOVIE_VOTE_COUNT = 12;
    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE_TEXT,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH_TEXT,
            MovieContract.MovieEntry.COLUMN_TITLE_TEXT,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH_TEXT,
            MovieContract.MovieEntry.COLUMN_POPULARITY_TEXT,
            MovieContract.MovieEntry.COLUMN_ADULT_TEXT,
            MovieContract.MovieEntry.COLUMN_GENERES_IDS_TEXT,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE_TEXT,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE_TEXT,
            MovieContract.MovieEntry.COLUMN_OVERVIEW_TEXT,
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE_TEXT,
            MovieContract.MovieEntry.COLUMN_VOTE_COUNT_TEXT
    };


    private static final String LOCAL_DATE_FORMAT = "yyyyMMdd";

    private static final String WHERE_ID = MovieContract.MovieEntry._ID + " = ?";
    private static final String WHERE_ALL = "";
    //private static final String WHERE_DAY = MovieContract.MovieEntry.COLUMN_END_DATE_TEXT + " IS NOT NULL AND substr(" + MovieContract.MovieEntry.COLUMN_START_DATE_TEXT + ",1,8) = ? OR " + "substr(" + MovieContract.MovieEntry.COLUMN_END_DATE_TEXT + ",1,8) = ?";
    //private static final String WHERE_DATES = MovieContract.MovieEntry.COLUMN_END_DATE_TEXT + " IS NOT NULL AND substr(" + MovieContract.MovieEntry.COLUMN_END_DATE_TEXT + ",1,8) >= ? AND " + "substr(" + MovieContract.MovieEntry.COLUMN_START_DATE_TEXT + ",1,8) <= ?";

    private Context mContext;

    public MovieManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public void createMovie(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie));
    }

    public boolean movieExist(Movie movie){
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, WHERE_ID, new String[]{String.valueOf(movie.getId())}, null);
        boolean ret = (cursor != null && cursor.moveToFirst());

        cursor.close();
        return ret;
    }

    public List<Movie> getMovies() {

        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, WHERE_ALL, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            List<Movie> movies = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    movies.add(getMovie(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return movies;
        }

        return Collections.emptyList();
    }

    public void updateMovie(Movie movie){
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }
        mContext.getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI, prepareMovieValues(movie), WHERE_ID, new String[]{String.valueOf(movie.getId())});
    }

    private ContentValues prepareMovieValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID, movie.getId());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE_TEXT, movie.getRelease_date());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH_TEXT, movie.getPoster_path());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE_TEXT, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH_TEXT, movie.getBackdrop_path());
        values.put(MovieContract.MovieEntry.COLUMN_POPULARITY_TEXT, movie.getPopularity());
        values.put(MovieContract.MovieEntry.COLUMN_ADULT_TEXT, movie.isAdult() ? 1 : 0);
        values.put(MovieContract.MovieEntry.COLUMN_GENERES_IDS_TEXT, MovieContract.getIntListToString(movie.getGenre_ids()));
        values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE_TEXT, movie.getOriginal_language());
        values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE_TEXT, movie.getOriginal_title());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW_TEXT, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE_TEXT, movie.getVote_average());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT_TEXT, movie.getVote_count());
        return values;
    }

    public void deleteMovie(Movie movie) {
        if (movie == null) {
            throw new NullPointerException("movie == null");
        }

        mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, WHERE_ID, new String[]{String.valueOf(movie.getId())});

    }

    private Movie getMovie(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(COL_MOVIE_ID));
        movie.setRelease_date(cursor.getLong(COL_MOVIE_RELEASE_DATE));
        movie.setPoster_path(cursor.getString(COL_MOVIE_POSTER_PATH));
        movie.setTitle(cursor.getString(COL_MOVIE_TITLE_TEXT));
        movie.setBackdrop_path(cursor.getString(COL_MOVIE_BACKDROP_PATH));
        movie.setPopularity(cursor.getFloat(COL_MOVIE_POPULARITY));

        movie.setAdult(cursor.getInt(COL_MOVIE_ADULT) != 0);
        String genres_ids_string = cursor.getString(COL_MOVIE_GENERES_IDS);
        movie.setGenre_ids(MovieContract.getStringToIntList(genres_ids_string));

        movie.setOriginal_language(cursor.getString(COL_MOVIE_ORIGINAL_LANGUAGE));
        movie.setOriginal_title(cursor.getString(COL_MOVIE_ORIGINAL_TITLE));
        movie.setOverview(cursor.getString(COL_MOVIE_OVERVIEW));
        movie.setVote_average(cursor.getFloat(COL_MOVIE_VOTE_AVERAGE));
        movie.setVote_count(cursor.getInt(COL_MOVIE_VOTE_COUNT));
        return movie;
    }
}
