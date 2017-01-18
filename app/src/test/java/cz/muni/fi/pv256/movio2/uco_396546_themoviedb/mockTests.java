package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Test;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieContract;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieManager;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.GenresContainer;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter.MainDiscoveredFragmentPresenter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter.MainFragmentPresenter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainDiscoveredFragment;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainFragment;

/**
 * Created by Huvart on 16/01/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class mockTests {

    @Mock
    Context mContext;

    @Test
    public void updateViewAproprietlyMainFragmentTest(){
        MainFragment mainFragment = Mockito.mock(MainFragment.class);
        when(mainFragment.getContext()).thenReturn(mContext);

        GenresContainer genresContainer = Mockito.mock(GenresContainer.class);

        when(genresContainer.getGenresList()).thenReturn(null);
        MainFragmentPresenter mainFragmentPresenter = new MainFragmentPresenter(mainFragment);

        mainFragmentPresenter.updateViewApropriatly(null);
        verify(mainFragment).setMainFragmentErrorLayout("NO DATA");
    }

    @Test
    public void updateViewAproprietlyDiscoveredFragmentTest(){
        MainDiscoveredFragment mainDiscoveredFragment = Mockito.mock(MainDiscoveredFragment.class);

        MainDiscoveredFragmentPresenter mainDiscoveredFragmentPresenter = new MainDiscoveredFragmentPresenter(mainDiscoveredFragment);
        MovieManager movieManager = mock(MovieManager.class);
        when(movieManager.getMovies()).thenReturn(null);
        mainDiscoveredFragmentPresenter.updateViewApropriatly(null, movieManager);
        verify(mainDiscoveredFragment).setMainFragmentErrorLayout("NO FAVOURITE MOVIES");
    }
}
