package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.interfaces;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.HamburgerMenuItem;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;

/**
 * Created by Huvart on 15/01/2017.
 */

public interface MainActivityPresenterInterface {

    public interface RequiredMainActivityOps{
        void setDetailMovieFragmentOnSide(Movie movie);
        void setDetailMovieFragmetOnTop(Movie movie);
        void setDiscoveredFragmentAsMain();
        void setMainFragmentAsMain();
        void setDetailMovieFragmentDefultOnSide();
        void mainFragmentUpdateViewApropriatly();
        void discoveredFragmentUpdateViewApropriatly();
        boolean isTwoPane();
    }

    public interface ProvidedMainActivityOps{
        void selectItemFromDrawer(int position);
        ArrayList<HamburgerMenuItem> getHamburgerMenuItems();
        void setMainActivityPresenter(Bundle savedInstanceState);
    }
}
