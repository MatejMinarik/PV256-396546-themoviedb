package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter;

import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters.MovieListRecyclerAdapter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.interfaces.MainActivityPresenterInterface;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.HamburgerMenuItem;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.sync.UpdaterSyncAdapter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainActivity;

/**
 * Created by Huvart on 15/01/2017.
 */

public class MainActivityPresenter implements MainActivityPresenterInterface.ProvidedMainActivityOps, MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener {
    boolean mTwoPane;
    boolean mDiscover = true;

    private ArrayList<HamburgerMenuItem> mHamburgerMenuItems = new ArrayList<>();

    private WeakReference<MainActivity> mMainActivityWeakReference;

    public MainActivityPresenter(MainActivity mainActivity){
        mMainActivityWeakReference = new WeakReference<MainActivity>(mainActivity);
        UpdaterSyncAdapter.initializeSyncAdapter(mainActivity.getApplicationContext());

        mHamburgerMenuItems.add(new HamburgerMenuItem(mainActivity.getString(R.string.DISCOVER)));
        mHamburgerMenuItems.add(new HamburgerMenuItem(mainActivity.getString(R.string.FAVOURITE)));
        mHamburgerMenuItems.add(new HamburgerMenuItem(mainActivity.getString(R.string.RELOAD)));
        mHamburgerMenuItems.add(new HamburgerMenuItem(mainActivity.getString(R.string.UPDATE_DATABASE)));
    }

    private MainActivity getMainActivity() throws NullPointerException{
        if(mMainActivityWeakReference != null){
            return mMainActivityWeakReference.get();
        }else{
            throw new NullPointerException("Main Activity is invalide");
        }
    }

    public ArrayList<HamburgerMenuItem> getHamburgerMenuItems(){
        return mHamburgerMenuItems;
    }

    @Override
    public void setMainActivityPresenter(Bundle savedInstanceState) {
        try {
            MainActivity mainActivity = getMainActivity();
            mTwoPane = getMainActivity().isTwoPane();
            if(mDiscover) {
                getMainActivity().setMainFragmentAsMain();
            }else{
                getMainActivity().setDiscoveredFragmentAsMain();
            }
            if(mTwoPane) {
                if (savedInstanceState == null) {
                    getMainActivity().setDetailMovieFragmentDefultOnSide();
                }
            }else{
                getMainActivity().getSupportActionBar().setElevation(0f);
            }

        }catch (NullPointerException e){
            mTwoPane = false;
            Log.e("MainActivityPresenter", e.toString());
        }
    }

    public void selectItemFromDrawer(int position){
        try {
            switch (position) {
                case 0:
                    if (!mDiscover) {
                        mDiscover = true;
                        getMainActivity().setMainFragmentAsMain();
                    }
                    break;
                case 1:
                    if (mDiscover) {
                        mDiscover = false;
                        getMainActivity().setDiscoveredFragmentAsMain();
                    }
                    break;
                case 2:
                    if (mDiscover) {
                        getMainActivity().mainFragmentUpdateViewApropriatly();
                    } else {
                        getMainActivity().discoveredFragmentUpdateViewApropriatly();
                    }
                    break;
                case 3:
                    UpdaterSyncAdapter.syncImmediately(getMainActivity().getApplicationContext());
                    break;
                default:
                    Log.e("selecting from drawer", position + " is undefined");
                    break;
            }
        }catch (NullPointerException e){
            Log.e("selectItemFromDrawer", e.toString());
        }

    }


    @Override
    public void onMovieSelect(Movie movie) {
        try {
            if(mTwoPane){
                getMainActivity().setDetailMovieFragmentOnSide(movie);
            }else {
                getMainActivity().setDetailMovieFragmetOnTop(movie);
            }
        }catch (NullPointerException e){
            return;
        }
    }
}
