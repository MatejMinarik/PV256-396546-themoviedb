package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Huvart on 08/01/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE_TEXT + " INTEGER," +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH_TEXT + " TEXT," +
                MovieContract.MovieEntry.COLUMN_TITLE_TEXT + " TEXT," +
                MovieContract.MovieEntry.COLUMN_BACKDROP_PATH_TEXT + " TEXT," +
                MovieContract.MovieEntry.COLUMN_POPULARITY_TEXT + " REAL, " +
                MovieContract.MovieEntry.COLUMN_ADULT_TEXT + " INTEGER," +
                MovieContract.MovieEntry.COLUMN_GENERES_IDS_TEXT + " TEXT," +
                MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE_TEXT + " TEXT," +
                MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE_TEXT + " TEXT," +
                MovieContract.MovieEntry.COLUMN_OVERVIEW_TEXT + " TEXT, " +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE_TEXT + " REAL," +
                MovieContract.MovieEntry.COLUMN_VOTE_COUNT_TEXT + " INTEGER);";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
