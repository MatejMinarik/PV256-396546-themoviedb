package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter;

import java.lang.ref.WeakReference;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieManager;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainDiscoveredFragment;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MovieDetailFragment;

/**
 * Created by Huvart on 16/01/2017.
 */

public class MovieDetailFragmentPresenter {

    private WeakReference<MovieDetailFragment> mMovieDetailFragmentWeakReference;

    boolean mFavourite;
    MovieManager mMovieManager;

    public MovieDetailFragmentPresenter(MovieDetailFragment movieDetailFragment){
        mMovieDetailFragmentWeakReference = new WeakReference<MovieDetailFragment>(movieDetailFragment);
    }

    public MovieDetailFragment getMovieDetailFragment() throws NullPointerException{
        if(mMovieDetailFragmentWeakReference != null){
            return mMovieDetailFragmentWeakReference.get();
        }else{
            throw new NullPointerException("Movie Detail Fragment is invalide");
        }
    }

    public void checkAndSetIfMovieInDatabase(Movie movie){
        mMovieManager = new MovieManager(getMovieDetailFragment().getContext());

        mFavourite = mMovieManager.movieExist(movie);
        getMovieDetailFragment().setFloatingButtonFavourite(mFavourite);
    }

    public void floatingButtonOnClickListenerAction(Movie movie){
        if(mFavourite){
            mMovieManager.deleteMovie(movie);
            mFavourite = false;
        }else{
            mMovieManager.createMovie(movie);
            mFavourite = true;
        }
        getMovieDetailFragment().setFloatingButtonFavourite(mFavourite);
    }

}
