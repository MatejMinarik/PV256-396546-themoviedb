package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view;


import android.content.Context;
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
import android.widget.TextView;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters.MovieListDiscoverRecyclerAdapter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters.MovieListRecyclerAdapter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter.MainDiscoveredFragmentPresenter;

/**
 * Created by Huvart on 12/01/2017.
 */

public class MainDiscoveredFragment extends Fragment {

    public static final String TAG = MainDiscoveredFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";

    private int mPosition = 0;
    private MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;
    private MovieListDiscoverRecyclerAdapter mAdapter;
    private Context mContext;


    private LayoutInflater mInflater;
    RecyclerView mRecyclerView;
    TextView mErrorTextView;

    MainDiscoveredFragmentPresenter mMainDiscoveredFragmentPresenter;

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

        mMainDiscoveredFragmentPresenter = new MainDiscoveredFragmentPresenter(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public MovieListDiscoverRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(MovieListDiscoverRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;

        View view = inflater.inflate(R.layout.saved_movies_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.saved_movies_recycler_view);
        mErrorTextView = (TextView) view.findViewById(R.id.error_text);

        mMainDiscoveredFragmentPresenter.updateViewApropriatly(savedInstanceState);
        return view;

    }

    @Override
    public void onDestroyView(){
        mRecyclerView = null;
        mErrorTextView = null;
        mInflater = null;
        super.onDestroyView();
    }

    public void updateView(){
        mMainDiscoveredFragmentPresenter.updateViewApropriatly(null);
    }

    public void setMainFragmentMainLayout(List<Movie> movies, Bundle savedInstanceState){
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.GONE);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setMovieAdapter(mRecyclerView, movies);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);

            if (mPosition != ListView.INVALID_POSITION) {
                mRecyclerView.smoothScrollToPosition(mPosition);
            }
        }
    }

    public void setMainFragmentErrorLayout(String errorMsg){
        mRecyclerView.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText("NO FAVOURITE MOVIES");
    }


    private void setMovieAdapter(RecyclerView filmRV, final List<Movie> movieList) {
        mAdapter = new MovieListDiscoverRecyclerAdapter(mInflater, mListener, mContext, movieList);
        filmRV.setAdapter(mAdapter);
    }
}