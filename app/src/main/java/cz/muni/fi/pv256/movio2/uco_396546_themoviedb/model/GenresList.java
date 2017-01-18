package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huvart on 07/11/2016.
 */

public class GenresList {

    public List<Genre> genres;

    GenresList(){
        genres = new ArrayList<>();
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}
