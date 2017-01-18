package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huvart on 23/10/2016.
 */

public class GenresContainer {


    private static GenresContainer sInstance;
    private ArrayList<Genre> mGenresList = new ArrayList<>();

    private GenresContainer() {
    }

    public static GenresContainer getInstance() {
        if (sInstance == null) {
            sInstance = new GenresContainer();
        }
        return sInstance;
    }



    public ArrayList<Genre> getGenresList() {
        return mGenresList;
    }

    public void setGenresList( ArrayList<Genre> genresList){
        mGenresList = genresList;
    }
}
