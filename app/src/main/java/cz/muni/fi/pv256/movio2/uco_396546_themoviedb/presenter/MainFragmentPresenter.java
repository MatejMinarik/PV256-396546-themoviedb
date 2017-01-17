package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.DownloadIntentService;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Genre;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.GenresContainer;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainFragment;

/**
 * Created by Huvart on 16/01/2017.
 */

public class MainFragmentPresenter {


    private ResponseReceiver mReceiver;

    private WeakReference<MainFragment> mMainFragmentWeakReference;

    public MainFragmentPresenter(MainFragment mainFragment){
        mMainFragmentWeakReference = new WeakReference<MainFragment>(mainFragment);
    }

    public MainFragment getMainFragment() throws NullPointerException{
        if(mMainFragmentWeakReference != null){
            return mMainFragmentWeakReference.get();
        }else{
            throw new NullPointerException("Main Fragment is invalide");
        }
    }

    public void onCreateRegisterReceiver(){
        try {
            mReceiver = new ResponseReceiver();
            IntentFilter intentFilter = new IntentFilter(ResponseReceiver.LOCAL_DOWNLOAD);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getMainFragment().getContext());
            localBroadcastManager.registerReceiver(mReceiver, intentFilter);
        }catch (NullPointerException e){
            Log.e("CreateRegisterReceiver", e.toString());
        }
    }

    public void onDestroyRegisterReceiver(){
        try {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getMainFragment().getContext());
            localBroadcastManager.unregisterReceiver(mReceiver);
        }catch (NullPointerException e){
            Log.e("DestroyRegisterReceiver", e.toString());
        }
    }

    public void startDownload() {
        try {
            Intent downloadGenreIntent = new Intent(getMainFragment().getContext(), DownloadIntentService.class);
            if(downloadGenreIntent != null) {
                downloadGenreIntent.putExtra(DownloadIntentService.IS_GENRE, true);
                getMainFragment().getContext().startService(downloadGenreIntent);
            }

            getMainFragment().notyfyStartDownload();
        }catch (NullPointerException e){
            Log.e("startDownload", e.toString());
        }
    }

    public void updateViewApropriatly(Bundle savedInstanceState){
        try {
            ArrayList<Genre> genreList = GenresContainer.getInstance().getGenresList();
            if (genreList != null && !genreList.isEmpty()) {
                if(isNetworkAvailable()) {
                    getMainFragment().setMainFragmentMainLayout(genreList, savedInstanceState);
                }else{
                    getMainFragment().setMainFragmentErrorLayout("NO NETWORK");
                }
            } else {
                getMainFragment().setMainFragmentErrorLayout("NO DATA");
            }

        }catch (NullPointerException e){
            Log.e("updateViewApropriatly", e.toString());
        }
    }

    public boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getMainFragment().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }catch (NullPointerException e){
            Log.e("isNetworkAvailable", e.toString());
        }
        return false;
    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String LOCAL_DOWNLOAD = "cz.muni.fi.pv256.movio2.uco_396546_themoviedb.MainFragmet.intent.action.LOCAL_DOWNLOAD";

        private int genres_downloaded = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            //save date from intent
            boolean isGenere = intent.getBooleanExtra(DownloadIntentService.IS_GENRE, false);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("Downloading")
                    .setContentText("Downloading movies to genres ")
                    .setSmallIcon(R.drawable.download_icon);

            if(isGenere) {// add Genres
                ArrayList<Genre> genres = intent.getParcelableArrayListExtra(DownloadIntentService.GENRES);
                GenresContainer.getInstance().setGenresList(genres);

                notifBuilder.setProgress(genres.size(),0,false);
                mNotificationManager.notify(MainFragment.notificationID, notifBuilder.build());

                for(int i=0;i<genres.size();i++){
                    Intent downloadGenreIntent = new Intent(context, DownloadIntentService.class);
                    downloadGenreIntent.putExtra(DownloadIntentService.IS_GENRE, false);
                    downloadGenreIntent.putExtra(DownloadIntentService.GENRE_APP_ID, i);
                    downloadGenreIntent.putExtra(DownloadIntentService.GENRE_INTERNET_ID, genres.get(i).getGenereId());
                    context.startService(downloadGenreIntent);
                }
                getMainFragment().updateView();
            }else{  //add Movies
                ArrayList<Movie> movies = intent.getParcelableArrayListExtra(DownloadIntentService.MOVIES);
                ArrayList<Genre> genres = GenresContainer.getInstance().getGenresList();
                int genreAppId = intent.getIntExtra(DownloadIntentService.GENRE_APP_ID, 0);
                genres_downloaded++;

                if(genres_downloaded < genres.size()) {
                    notifBuilder.setProgress(genres.size(), genreAppId, false);
                    mNotificationManager.notify(MainFragment.notificationID, notifBuilder.build());
                }else{
                    notifBuilder.setProgress(0, 0, false);
                    notifBuilder.setContentTitle("Downloading done");
                    mNotificationManager.notify(MainFragment.notificationID, notifBuilder.build());
                }

                genres.get(genreAppId).setMovieList(movies);
                getMainFragment().getAdapter().notifyItemChanged(genreAppId);
            }

        }
    }

}
