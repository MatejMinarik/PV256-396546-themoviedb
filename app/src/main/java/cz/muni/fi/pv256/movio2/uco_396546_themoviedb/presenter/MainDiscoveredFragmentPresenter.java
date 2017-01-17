package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieManager;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainDiscoveredFragment;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainFragment;

/**
 * Created by Huvart on 16/01/2017.
 */

public class MainDiscoveredFragmentPresenter {

    private WeakReference<MainDiscoveredFragment> mMainDiscoveredFragmentWeakReference;

    public MainDiscoveredFragmentPresenter(MainDiscoveredFragment mainDiscoveredFragment){
        mMainDiscoveredFragmentWeakReference = new WeakReference<MainDiscoveredFragment>(mainDiscoveredFragment);
    }

    public MainDiscoveredFragment getMainDiscoveredFragment() throws NullPointerException{
        if(mMainDiscoveredFragmentWeakReference != null){
            return mMainDiscoveredFragmentWeakReference.get();
        }else{
            throw new NullPointerException("Main Discovered Fragment is invalide");
        }
    }


    public void updateViewApropriatly(Bundle savedInstanceState) {
        try {
            MovieManager movieManager = new MovieManager(getMainDiscoveredFragment().getContext());
            updateViewApropriatly(savedInstanceState, movieManager);
        }catch (NullPointerException e){
            Log.e("updateViewApropriatly", e.toString());
        }
    }

    public void updateViewApropriatly(Bundle savedInstanceState,MovieManager movieManager) {
        try {
            List<Movie> movies = movieManager.getMovies();
            if (movies != null && !movies.isEmpty()) {
                getMainDiscoveredFragment().setMainFragmentMainLayout(movies, savedInstanceState);
            } else {
                getMainDiscoveredFragment().setMainFragmentErrorLayout(getMainDiscoveredFragment().getString(R.string.NO_FAVOURITE_MOVIES));
            }
        }catch (NullPointerException e){
            Log.e("updateViewApropriatly", e.toString());
        }
    }
}
