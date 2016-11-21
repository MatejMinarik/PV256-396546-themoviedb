package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Huvart on 10/10/16.
 */

public class MoviesContainer {

    private static MoviesContainer sInstance;
    private ArrayList<Movie> mMovieList = new ArrayList<>();

    private MoviesContainer() {
        initFilmList();
    }

    public static MoviesContainer getInstance() {
        if (sInstance == null) {
            sInstance = new MoviesContainer();
        }
        return sInstance;
    }

    private void initFilmList() {
        for (int i = 0; i < 5 ; i++) {
            mMovieList.add(new Movie(getCurrentTime().getTime(), "this is first cover", "this is first title", "this is first backdrop", 0.71f, R.drawable.stolety_starik));
            mMovieList.add(new Movie(getCurrentTime().getTime(), "this is second cover", "this is second title", "this is second backdrop", 0.99f, R.drawable.letec));
            mMovieList.add(new Movie(getCurrentTime().getTime(), "this is third cover", "this is third title", "this is third backdrop", 0.05f, R.drawable.americka_krasa));
        }
    }

    private Date getCurrentTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        return cal.getTime();
    }

    public ArrayList<Movie> getMovieList() {
        return mMovieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        mMovieList = movieList;
    }
}
