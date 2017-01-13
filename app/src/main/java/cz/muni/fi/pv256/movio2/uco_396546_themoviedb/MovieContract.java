package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Huvart on 08/01/2017.
 */

public class MovieContract {


    public static final String CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio2.uco_396546_themoviedb.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "uco_396546_themoviedb";

    public static String getIntListToString(List<Integer> genresIds) {
        String ret = "";
        for(int i=0;i<genresIds.size();i++){
            ret += Integer.toString(genresIds.get(i)) + ",";
        }
        return ret;
    }

    public static List<Integer> getStringToIntList(String genresIds) {
        List<String> array = Arrays.asList(genresIds.split(","));
        List<Integer> ret = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            ret.add(Integer.getInteger(array.get(i)));
        }
        return ret;
    }

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_RELEASE_DATE_TEXT = "release_date";
        public static final String COLUMN_POSTER_PATH_TEXT = "poster_path";
        public static final String COLUMN_TITLE_TEXT = "title";
        public static final String COLUMN_BACKDROP_PATH_TEXT = "backdrop_path";
        public static final String COLUMN_POPULARITY_TEXT = "popularity";
        public static final String COLUMN_ADULT_TEXT = "adult";
        public static final String COLUMN_GENERES_IDS_TEXT = "genre_ids";
        public static final String COLUMN_ORIGINAL_LANGUAGE_TEXT = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE_TEXT = "original_title";
        public static final String COLUMN_OVERVIEW_TEXT = "overview";
        public static final String COLUMN_VOTE_AVERAGE_TEXT = "vote_average";
        public static final String COLUMN_VOTE_COUNT_TEXT = "vote_count";

        public static Uri buildWorkTimeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
