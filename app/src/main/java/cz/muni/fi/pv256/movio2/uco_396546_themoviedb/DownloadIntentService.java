package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
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

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.DownloadApiInterface;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Genre;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.GenreMoviesList;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.GenresList;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter.MainFragmentPresenter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainFragment;
import okhttp3.OkHttpClient;

/**
 * Created by Huvart on 04/11/2016.
 */

public class DownloadIntentService extends IntentService {

    public static final String TEXT_IN = "text in";
    public static final String TEXT_OUT = "text out";
    public static final String IS_GENRE = "isGenre";
    public static final String IS_SINGLE_MOVIE = "isSingleMovie";
    public static final String MOVIE_ID = "movie_id";
    public static final String GENRES = "genres";
    public static final String MOVIES = "movies";
    public static final String GENRE_APP_ID = "genre app id";
    public static final String GENRE_INTERNET_ID = "genre internal id";

    private static final OkHttpClient client = new OkHttpClient();

    public DownloadIntentService(){
        super(DownloadIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
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

        //process download
        intent.getStringExtra(TEXT_IN);
        boolean isGenre = intent.getBooleanExtra(IS_GENRE, false);
        if(BuildConfig.LOGING) {
            Log.d("Intent service", "downloding: is called");
        }

        List<Genre> genres = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        if(isGenre){//Genres

            DownloadApiInterface apiService = RetrofitClient.getClient(deserializer).create(DownloadApiInterface.class);

            retrofit2.Call<GenresList> call = apiService.getGenres(AppData.api_key, "en-US");

            try {
                GenresList genresList = call.execute().body();
                if(genresList != null){
                    genres = genresList.getGenres();
                }else{
                    if(BuildConfig.LOGING) {
                        Log.d("no data downloaded", call.request().toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{//Movies
            int mGenreInternetId = intent.getIntExtra(DownloadIntentService.GENRE_INTERNET_ID, 0);

            DownloadApiInterface apiService = RetrofitClient.getClient(deserializer).create(DownloadApiInterface.class);

            retrofit2.Call<GenreMoviesList> call = apiService.getGenreMovies(mGenreInternetId, AppData.api_key, "en-US", "created_at.asc");

            try {
                GenreMoviesList genreMoviesList = call.execute().body();
                if (genreMoviesList != null) {
                    movies = genreMoviesList.getMovies();
                } else {
                    if(BuildConfig.LOGING) {
                        Log.d("no data downloaded", call.request().toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //Broadcast
        Intent brocastIntent = new Intent();
        brocastIntent.setAction(MainFragmentPresenter.ResponseReceiver.LOCAL_DOWNLOAD);
        brocastIntent.addCategory(Intent.CATEGORY_DEFAULT);

        brocastIntent.putExtra(IS_GENRE, isGenre);
        if(isGenre){//Genres
            brocastIntent.putExtra(GENRES, (ArrayList<Genre>)genres);
        }else{//Movies
            brocastIntent.putExtra(MOVIES, (ArrayList<Movie>)movies);
            brocastIntent.putExtra(GENRE_INTERNET_ID, intent.getIntExtra(DownloadIntentService.GENRE_INTERNET_ID, 0));
            brocastIntent.putExtra(GENRE_APP_ID, intent.getIntExtra(DownloadIntentService.GENRE_APP_ID, 0));
        }

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(brocastIntent);
    }


}

