package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.AppData;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.DownloadApiInterface;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.DownloadIntentService;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.Genre;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.GenresContainer;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.GenresList;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.MainFragment;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.RetrofitClient;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieManager;

/**
 * Created by Huvart on 14/01/2017.
 */

public class UpdaterSyncAdapter extends AbstractThreadedSyncAdapter {

    // Interval at which to sync with the server, in seconds.
    public static final int SYNC_INTERVAL = 60 * 60 * 24; //day
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    public UpdaterSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder()
                    .syncPeriodic(syncInterval, flexTime)
                    .setSyncAdapter(account, authority)
                    .setExtras(Bundle.EMPTY) //enter non null Bundle, otherwise on some phones it crashes sync
                    .build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one if the
     * fake account doesn't exist yet.  If we make a new account, we call the onAccountCreated
     * method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

            /*
             * Add the account and account type, no password or user data
             * If successful, return the Account object, otherwise report an error.
             */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
    /*
     * Since we've created an account
     */
        UpdaterSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

    /*
     * Without calling setSyncAutomatically, our periodic sync will not be enabled.
     */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

    /*
     * Finally, let's do a sync to get things started
     */
        syncImmediately(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        MovieManager movieManager = new MovieManager(getContext());
        List<Movie> movies = movieManager.getMovies();

        JsonDeserializer<Long> deserializer = new JsonDeserializer<Long>() {
            @Override
            public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){

                if(json==null){
                    return new Long(0);
                }
                else{
                    String dateString = json.getAsString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
                    long dateLong = 0;
                    try {
                        Date releaseDate = simpleDateFormat.parse(dateString);
                        dateLong = releaseDate.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return new Long(dateLong);
                }
            }
        };


        for (int i=0;i<movies.size();i++){
            DownloadApiInterface apiService = RetrofitClient.getClient(deserializer).create(DownloadApiInterface.class);

            retrofit2.Call<Movie> call = apiService.getMovie(movies.get(i).getId(), AppData.api_key, "en-US");

            Movie reference_movie = movies.get(i);
            Log.d("Sync Adapter", "ubdating movie " + reference_movie.getTitle());
            try {
                Movie movie = call.execute().body();
                if(movie != null){
                    processCompareUpdateMovies(reference_movie, movie, movieManager);
                }else{
                    Log.d("no data downloaded", call.request().toString());
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void processCompareUpdateMovies(Movie reference_movie, Movie downloaded_movie, MovieManager movieManager){
        boolean somethingUpdated = false;
        String updateMsg = "";
        if(downloaded_movie.getRelease_date().compareTo(reference_movie.getRelease_date()) != 0){
            somethingUpdated = true;
            updateMsg += "movie " + downloaded_movie.getTitle() + " changed released date\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getOriginal_title().compareTo(reference_movie.getOriginal_title()) != 0){
            somethingUpdated = true;
            updateMsg += "movie " + reference_movie.getOriginal_title() + " changed original title to " + downloaded_movie.getOriginal_title() + "\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getBackdrop_path().compareTo(reference_movie.getBackdrop_path()) != 0){
            somethingUpdated = true;
            updateMsg += "movie " + downloaded_movie.getTitle() + " changed backdrop poster\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getPoster_path().compareTo(reference_movie.getPoster_path()) != 0){
            somethingUpdated = true;
            updateMsg += "movie " + downloaded_movie.getTitle() + " changed poster\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getOriginal_language().compareTo(reference_movie.getOriginal_language()) != 0){
            somethingUpdated = true;
            updateMsg += "movie " + downloaded_movie.getOriginal_language() + " changed original language to" + downloaded_movie.getOriginal_language() + "\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getOverview().compareTo(reference_movie.getOverview()) != 0){
            somethingUpdated = true;
            updateMsg += "movie " + downloaded_movie.getTitle() + " changed overview description\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getPopularity() != reference_movie.getPopularity()){
            somethingUpdated = true;
            updateMsg += "movie " + downloaded_movie.getTitle() + " changed popularity\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getVote_count() != reference_movie.getVote_count()){
            somethingUpdated = true;
            updateMsg += "movie " + downloaded_movie.getTitle() + " changed vote count\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getVote_average() != reference_movie.getVote_average()){
            somethingUpdated = true;
            updateMsg += "movie " + downloaded_movie.getTitle() + " changed vote average\n";
            movieManager.updateMovie(downloaded_movie);
        }
        if(downloaded_movie.getTitle().compareTo(reference_movie.getTitle()) != 0){
            somethingUpdated = true;
            updateMsg += "movie " + reference_movie.getTitle() + " changed title to " + downloaded_movie.getTitle() + "\n";
            movieManager.updateMovie(downloaded_movie);
        }

        if(somethingUpdated){
            NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getContext())
                    .setContentTitle("Updating movie database")
                    .setContentText(updateMsg)
                    .setSmallIcon(R.drawable.download_icon);

            mNotificationManager.notify(MainFragment.notificationID, notifBuilder.build());
        }
    }
}

