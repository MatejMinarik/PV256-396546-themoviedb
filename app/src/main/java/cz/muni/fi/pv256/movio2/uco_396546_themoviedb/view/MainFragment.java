package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Genre;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters.GenresListRecyclerAdapter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters.MovieListRecyclerAdapter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter.MainFragmentPresenter;

/**
 * Created by Huvart on 10/10/16.
 */

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";
    public static final int notificationID = 1;

    private int mPosition = 0;
    private MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;
    private Context mContext;
    private GenresListRecyclerAdapter mAdapter;
    private LayoutInflater mInflater;

    RecyclerView mRecyclerView;
    TextView mErrorTextView;

    MainFragmentPresenter mMainFragmentPresenter;

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

        mMainFragmentPresenter = new MainFragmentPresenter(this);
        mContext = getActivity().getApplicationContext();

        mMainFragmentPresenter.onCreateRegisterReceiver();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mMainFragmentPresenter.onDestroyRegisterReceiver();
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

        mMainFragmentPresenter.startDownload();

        View view = inflater.inflate(R.layout.movie_genres_list_recycle_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_genres);
        mErrorTextView = (TextView) view.findViewById(R.id.error_text);

        mMainFragmentPresenter.updateViewApropriatly(savedInstanceState);

        return view;
    }

    @Override
    public void onDestroyView(){
        mRecyclerView = null;
        mErrorTextView = null;
        mInflater = null;
        super.onDestroyView();
    }

    public void notyfyStartDownload(){
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(mContext)
                .setContentTitle("Downloading")
                .setContentText("Downloading genres ")
                .setSmallIcon(R.drawable.download_icon);
        mNotificationManager.notify(notificationID, notifBuilder.build());
    }

    public void updateView(){
        mMainFragmentPresenter.updateViewApropriatly(null);
    }

    public void setMainFragmentMainLayout(ArrayList<Genre> genreList, Bundle savedInstanceState){
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.GONE);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setGenreAdapter(mRecyclerView, genreList);

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
        mErrorTextView.setText(errorMsg);
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

    private void setGenreAdapter(RecyclerView filmRV, final ArrayList<Genre> genreList) {
        mAdapter = new GenresListRecyclerAdapter(mInflater, mListener, mContext, genreList);
        filmRV.setAdapter(mAdapter);
    }

}
