package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

/**
 * Created by Huvart on 28/10/2016.
 */

public class AppData {

    public static String base_url = "https://api.themoviedb.org/";
    public static String movie_genere_url(int genere_id){
        return base_url + "/discover/movie?api_key=0b1ade6bdad5ce4b69c4075972d90d36&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=true&page=1&with_genres=" + genere_id;
    }
    public static String movie_url(int id){
        return base_url + "/movie/" + Integer.toString(id) + "?api_key=0b1ade6bdad5ce4b69c4075972d90d36&language=en-US";
    }
    public static String genere_list_url(){
        return base_url + "/genre/movie/list?api_key=0b1ade6bdad5ce4b69c4075972d90d36&language=en-US";
    }
    public static String base_picture_url = "https://image.tmdb.org/t/p/w500";
    public static String api_key = "0b1ade6bdad5ce4b69c4075972d90d36";
}
