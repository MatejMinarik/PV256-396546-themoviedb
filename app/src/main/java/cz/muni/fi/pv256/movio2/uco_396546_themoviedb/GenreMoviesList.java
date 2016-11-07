package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huvart on 07/11/2016.
 */

public class GenreMoviesList {

    private List<Movie> results;
    private int id;
    private int page;
    private int total_pages;
    private int total_results;

    GenreMoviesList(){
        results = new ArrayList<>();
        id = 0;
        page = 0;
        total_pages = 0;
        total_results = 0;
    }

    public List<Movie> getMovies() {
        return results;
    }

    public void setMovies(List<Movie> results) {
        this.results = results;
    }

    public int getGanreId() {
        return id;
    }

    public void setGanreId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
