package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Huvart on 10/10/16.
 */

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";

    private int mPosition = 0;
    private MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private GenresListRecyclerAdapter mAdapter;
    LayoutInflater mInflater;
    ViewGroup mViewGroup;
    Bundle mBundle;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mListener = (MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "Activity must implement MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener", e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null; //Avoid leaking the Activity
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity().getApplicationContext();
    }

    public GenresListRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(GenresListRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mViewGroup = container;
        mBundle = savedInstanceState;

        return createApropriateView(inflater, container, savedInstanceState);

    }

    public void updateView(){
        View thisView = this.getView();
        View newView = createApropriateView(mInflater, mViewGroup, mBundle);
        replaceView(thisView, newView);

    }

    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);
        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }

    public View createApropriateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ArrayList<Genre> genreList = GenresContainer.getInstance(this).getGenresList();
        View view;
        if(isNetworkAvailable()) {
            if (genreList != null && !genreList.isEmpty()) {
                view = inflater.inflate(R.layout.movie_genres_list_recycle_fragment, container, false);
                Log.i("onCreateView:", "inflate movie_genres_list_recycle_fragment");

                fillRecycleView(view, genreList);
                if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
                    mPosition = savedInstanceState.getInt(SELECTED_KEY);

                    if (mPosition != ListView.INVALID_POSITION) {
                        mRecyclerView.smoothScrollToPosition(mPosition);
                    }
                }
            } else {
                view = inflater.inflate(R.layout.no_data_fragmet, container, false);
                Log.i("onCreateView:", "inflate empty");
            }
        }else{
            view = inflater.inflate(R.layout.no_network_fragment, container, false);
            Log.i("onCreateView:", "no network");
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    private void fillRecycleView(View rootView, ArrayList<Genre> genreList) {
        // get data

        //if (genreList != null && !genreList.isEmpty()){
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_genres);
            mRecyclerView.setHasFixedSize(true);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            setAdapter(mRecyclerView, genreList);
        //}
    }

    private void setAdapter(RecyclerView filmRV, final ArrayList<Genre> genreList) {
        mAdapter = new GenresListRecyclerAdapter(mListener, mContext, genreList);
        filmRV.setAdapter(mAdapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
