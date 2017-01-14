package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Huvart on 07/11/2016.
 */

public interface DownloadApiInterface {
    @GET("3/genre/movie/list")
    Call<GenresList> getGenres(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/genre/{genre_id}/movies")
    Call<GenreMoviesList> getGenreMovies(@Path("genre_id") int genre_id, @Query("api_key") String apiKey, @Query("language") String langage, @Query("sort_by") String sortBy);

    @GET("3/movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int movie_id, @Query("api_key") String apiKey, @Query("language") String langage);
}
