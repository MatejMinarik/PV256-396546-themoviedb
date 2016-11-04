package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

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
    private final WeakReference<MainFragment> mFragmentWeakReference;

    public GenreDownloader(MainActivity mainActivity) {

        mActivityWeakReference = new WeakReference<>(mainActivity);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        if (isCancelled()) {
            Log.d("cancelling calculation ", ":(");
            return null;
        }
        Request request = new Request.Builder()
                .url(AppData.genere_list_url)
                .build();

        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response != null){
            publishProgress(response.toString());
        }
        return 0;
    }

    private void setDownloadedData(String genereData){
        MainFragment mMainFragment = mFragmentWeakReference.get();
        if(mMainFragment == null){
            return;
        }
        JSONObject jobject = new JSONObject(genereData);
        JSONArray jarray_genere = jobject.getJSONArray("genres");

        for(int i=0;i<jarray_genere.length();i++){
            JSONObject jgenere = jarray_genere.getJSONObject(i);
            jgenere.getInt("id");
            jgenere.getString("name");

        }
        mMainFragment.addNewGenre();
        //set new data
        //mMainActivity
    }

    @Override
    protected void onProgressUpdate(String... values){
        setDownloadedData(values[0]);
    }

}
