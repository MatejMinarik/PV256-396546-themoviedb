package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

//import okhttp3.Call;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Huvart on 27/10/2016.
 */

public class GenreDownloader extends AsyncTask<Void, String, Integer> {

    private static final OkHttpClient client = new OkHttpClient();
    private final WeakReference<GenresContainer> mContainerWeakReference;

    public GenreDownloader(GenresContainer genresContainer) {

        mContainerWeakReference = new WeakReference<GenresContainer>(genresContainer);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Request request = new Request.Builder()
                .url(AppData.genere_list_url())
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

    private void setDownloadedData(String genereData){
        GenresContainer mGenresContainer = mContainerWeakReference.get();
        if(mGenresContainer == null){
            return;
        }
        JSONObject jobject = null;
        List<Genre> genres = new ArrayList<>();
        try {
            Gson jsonParser = new Gson();
            //jsonParser.fromJson();
            jobject = new JSONObject(genereData);
            JSONArray jarray_genere = jobject.getJSONArray("genres");

            for(int i=0;i<jarray_genere.length();i++){
                JSONObject jgenere = jarray_genere.getJSONObject(i);
                Genre genereNew = jsonParser.fromJson(jgenere.toString(), Genre.class);
                //int genreId = jgenere.getInt("id");
                //String genreName = jgenere.getString("name");
                //Genre genereNew = new Genre(genreName, genreId, genreMovies);
                Log.d("Genre info", Integer.toString(genereNew.getGenereId()));
                genres.add(genereNew);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mGenresContainer.addGenres(genres);
    }

    @Override
    protected void onProgressUpdate(String... values){
        Log.d("set downloadedData" , values[0]);
        setDownloadedData(values[0]);
    }

}
