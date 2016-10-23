package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Huvart on 23/10/2016.
 */

public class GenresContainer {


    private static GenresContainer sInstance;
    private ArrayList<Genre> mGenresList = new ArrayList<>();

    private GenresContainer() {initGenreMovieList(); }

    public static GenresContainer getInstance() {
        if (sInstance == null) {
            sInstance = new GenresContainer();
        }
        return sInstance;
    }

    private void initGenreMovieList() {
        mGenresList.add(new Genre("Action", MoviesContainer.getInstance().getMovieList()));
        mGenresList.add(new Genre("Comedy", MoviesContainer.getInstance().getMovieList()));
        mGenresList.add(new Genre("Crime", MoviesContainer.getInstance().getMovieList()));
        mGenresList.add(new Genre("Documentary", MoviesContainer.getInstance().getMovieList()));
    }

    public ArrayList<Genre> getGenresList() {
        return mGenresList;
    }
}
