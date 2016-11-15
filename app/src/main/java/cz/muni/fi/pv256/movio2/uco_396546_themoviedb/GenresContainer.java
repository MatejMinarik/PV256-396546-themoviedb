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

    /*public void addGenres(List<Genre> genres){
        for(int i = 0;i<genres.size();i++){
            addGenre(genres.get(i));
        }
        Log.d("add genres", "should add genres");
    }


    public void addMovies(int genreAppId, List<Movie> movies){
        for (int i=0;i<movies.size();i++){
            addMovie(genreAppId, movies.get(i));
        }
    }

    public void addMovie(int genreAppId, Movie movie){
        mGenresList.get(genreAppId).getMovieList().add(movie);
        /*if(mMainFragment.getAdapter() != null) {
            mMainFragment.getAdapter().notifyItemChanged(genreAppId);
        }
    }*/

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
