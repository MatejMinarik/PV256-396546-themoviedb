package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Huvart on 07/11/2016.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(JsonDeserializer<Long> deserializer) {
        if (retrofit==null) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Long.class, deserializer)
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(AppData.base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;



    }


}
