package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Huvart on 28/10/2016.
 */

public class GenresMoviesDownloader extends AsyncTask<Void, String, Integer> {


    private static final OkHttpClient client = new OkHttpClient();
    private final WeakReference<GenresContainer> mContainerWeakReference;
    private int mGenreInternetId;
    private int mGenreAppId;

    public GenresMoviesDownloader(GenresContainer genresContainer, int genreAppId, int genreInternetId) {
        mGenreAppId = genreAppId;
        mGenreInternetId = genreInternetId;
        mContainerWeakReference = new WeakReference<GenresContainer>(genresContainer);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Request request = new Request.Builder()
                .url(AppData.movie_genere_url(mGenreInternetId))
                .build();

        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isCancelled()) {
            return null;
        }
        if(response != null){
            try {
                publishProgress(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private void setDownloadedData(String MoviesData){
        GenresContainer genresContainer = mContainerWeakReference.get();
        if(genresContainer == null){
            return;
        }
        JSONObject jobject = null;
        List<Movie> movies = new ArrayList<>();
        try {
            jobject = new JSONObject(MoviesData);
            JSONArray jarray_movie = jobject.getJSONArray("results");
            Gson jsonParser = new Gson();

            for(int i=0;i<jarray_movie.length();i++){
                JSONObject jmovie = jarray_movie.getJSONObject(i);

                String movieTimeReleaseString = jmovie.getString("release_date");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date releaseDate = simpleDateFormat.parse(movieTimeReleaseString);

                Movie movieNew = jsonParser.fromJson(jmovie.toString(), Movie.class);
                movieNew.setReleaseDate(releaseDate.getTime());

                movies.add(movieNew);
            }
            Log.d("downloaded movies ", Integer.toString(jarray_movie.length()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        genresContainer.addMovies(mGenreAppId, movies);
    }

    @Override
    protected void onProgressUpdate(String... values){
        Log.d("set downloadedData" , values[0]);
        setDownloadedData(values[0]);
    }

}
