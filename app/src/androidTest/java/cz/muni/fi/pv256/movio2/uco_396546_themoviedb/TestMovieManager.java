package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieContract;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieManager;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;

/**
 * Created by Huvart on 12/01/2017.
 */

public class TestMovieManager extends AndroidTestCase {

    private static final String TAG = TestMovieManager.class.getSimpleName();

    private MovieManager mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new MovieManager(mContext);
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
    }

    public void testGetMovies() throws Exception {
        List<Movie> expectedMovies = new ArrayList<>(2);
        Movie movie1 = createMovie(11, "ahoj", new Long(444444));
        Movie movie2 = createMovie(21, "bububu", new Long(6666666));
        expectedMovies.add(movie1);
        expectedMovies.add(movie2);

        mManager.createMovie(movie1);
        mManager.createMovie(movie2);

        List<Movie> movies = mManager.getMovies();
        Log.d(TAG, movies.toString());
        assertTrue(movies.size() == 2);
    }

    public void testMovieExist() throws Exception {
        Movie movie1 = createMovie(1, "ahoj",   new Long(4444444));
        Movie movie2 = createMovie(2, "bububu", new Long(6666666));
        Movie movie3 = createMovie(3, "habubu", new Long(9999999));

        mManager.createMovie(movie1);
        mManager.createMovie(movie2);

        assertTrue(mManager.movieExist(movie1));
        assertTrue(mManager.movieExist(movie2));
        assertTrue(!mManager.movieExist(movie3));
    }

    public void testMovieUpdate() throws Exception{
        Movie movie1 = createMovie(9, "ahoj1",   new Long(4444444));
        Movie movie2 = createMovie(9, "bububu1", new Long(6666666));
        Movie movie3 = createMovie(55, "habubu1", new Long(9999999));

        mManager.createMovie(movie1);
        mManager.createMovie(movie3);

        mManager.updateMovie(movie2);

        List<Movie> movies = mManager.getMovies();
        assertTrue(mManager.movieExist(movie1));
        assertTrue(mManager.movieExist(movie2));
        assertTrue(mManager.movieExist(movie3));
        assertTrue(movies.size() == 2);
    }

    private Movie createMovie(int id, String title, Long releaseDate) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setRelease_date(releaseDate);
        return movie;
    }
}
