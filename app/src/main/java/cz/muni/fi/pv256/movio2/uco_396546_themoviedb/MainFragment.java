package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
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
    private ResponseReceiver mReceiver;

    RecyclerView mRecyclerView;
    TextView mErrorTextView;

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

        mReceiver = new ResponseReceiver();
        IntentFilter intentFilter = new IntentFilter(MainFragment.ResponseReceiver.LOCAL_DOWNLOAD);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        localBroadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onDestroy(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        localBroadcastManager.unregisterReceiver(mReceiver);
        super.onDestroy();
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

        startDownload();

        View view = inflater.inflate(R.layout.movie_genres_list_recycle_fragment, container, false);
        Log.i("onCreateView:", "inflate movie_genres_list_recycle_fragment");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_genres);
        mErrorTextView = (TextView) view.findViewById(R.id.error_text);

        updateViewApropriatly(savedInstanceState);

        return view;


    }

    private void startDownload() {
        Intent downloadGenreIntent = new Intent(mContext, DownloadIntentService.class);
        downloadGenreIntent.putExtra(DownloadIntentService.IS_GENRE, true);
        mContext.startService(downloadGenreIntent);

        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(mContext)
                .setContentTitle("Downloading")
                .setContentText("Downloading genres ")
                .setSmallIcon(R.drawable.download_icon);
        mNotificationManager.notify(notificationID, notifBuilder.build());
    }

    public void updateView(){
        updateViewApropriatly(null);

    }

    public void updateViewApropriatly(Bundle savedInstanceState){
        ArrayList<Genre> genreList = GenresContainer.getInstance().getGenresList();
        if(isNetworkAvailable()) {
            if (genreList != null && !genreList.isEmpty()) {
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
            } else {
                mRecyclerView.setVisibility(View.GONE);
                mErrorTextView.setVisibility(View.VISIBLE);
                mErrorTextView.setText("NO DATA");
                Log.i("onCreateView:", "inflate empty");
            }
        }else{
            mRecyclerView.setVisibility(View.GONE);
            mErrorTextView.setVisibility(View.VISIBLE);
            mErrorTextView.setText("NO NETWORK");
            Log.i("onCreateView:", "no network");
        }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String LOCAL_DOWNLOAD = "cz.muni.fi.pv256.movio2.uco_396546_themoviedb.MainFragmet.intent.action.LOCAL_DOWNLOAD";

        private int genres_downloaded = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            //save date from intent
            boolean isGenere = intent.getBooleanExtra(DownloadIntentService.IS_GENRE, false);
            NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(mContext)
                    .setContentTitle("Downloading")
                    .setContentText("Downloading movies to genres ")
                    .setSmallIcon(R.drawable.download_icon);

            if(isGenere) {// add Genres
                ArrayList<Genre> genres = intent.getParcelableArrayListExtra(DownloadIntentService.GENRES);
                GenresContainer.getInstance().setGenresList(genres);

                notifBuilder.setProgress(genres.size(),0,false);
                mNotificationManager.notify(notificationID, notifBuilder.build());

                for(int i=0;i<genres.size();i++){
                    Intent downloadGenreIntent = new Intent(mContext, DownloadIntentService.class);
                    downloadGenreIntent.putExtra(DownloadIntentService.IS_GENRE, false);
                    downloadGenreIntent.putExtra(DownloadIntentService.GENRE_APP_ID, i);
                    downloadGenreIntent.putExtra(DownloadIntentService.GENRE_INTERNET_ID, genres.get(i).getGenereId());
                    mContext.startService(downloadGenreIntent);
                }
                updateView();
            }else{  //add Movies
                ArrayList<Movie> movies = intent.getParcelableArrayListExtra(DownloadIntentService.MOVIES);
                ArrayList<Genre> genres = GenresContainer.getInstance().getGenresList();
                int genreAppId = intent.getIntExtra(DownloadIntentService.GENRE_APP_ID, 0);
                genres_downloaded++;

                if(genres_downloaded < genres.size()) {
                    notifBuilder.setProgress(genres.size(), genreAppId, false);
                    mNotificationManager.notify(notificationID, notifBuilder.build());
                }else{
                    notifBuilder.setProgress(0, 0, false);
                    notifBuilder.setContentTitle("Downloading done");
                    mNotificationManager.notify(notificationID, notifBuilder.build());
                }

                genres.get(genreAppId).setMovieList(movies);
                getAdapter().notifyItemChanged(genreAppId);
            }

        }
    }

}
