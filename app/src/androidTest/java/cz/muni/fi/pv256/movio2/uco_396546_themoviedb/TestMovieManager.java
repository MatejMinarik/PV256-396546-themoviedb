package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huvart on 12/01/2017.
 */

public class TestMovieManager extends AndroidTestCase {

    private static final String TAG = TestMovieManager.class.getSimpleName();

    private MovieManager mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new TestMovieManager(mContext);
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
        Movie movie1 = createMovie(1, "ahoj", 444444);
        Movie movie2 = createMovie(2, "bububu", 6666666);
        expectedMovies.add(movie1);
        expectedMovies.add(movie2);

        mManager.createMovie(movie1);
        mManager.createMovie(movie2);
        //mManager.createWorkTime(workTime2);

        List<Movie> movies = mManager.getMovies();
        Log.d(TAG, movies.toString());
        assertTrue(movies.size() == 2);
        assertEquals(expectedMovies, movies);
    }

    public void testMovieExist() throws Exception {
        Movie movie1 = createMovie(1, "ahoj", 444444);
        Movie movie2 = createMovie(2, "bububu", 6666666);
        Movie movie3 = createMovie(3, "habubu", 9999999);

        mManager.createMovie(movie1);
        mManager.createMovie(movie3);

        assertTrue(mManager.movieExist(movie1));
        assertTrue(mManager.movieExist(movie2));
        assertTrue(!mManager.movieExist(movie3));
    }

    private Movie createMovie(int id, String title, Long releaseDate) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setRelease_date(releaseDate);
        return workTime;
    }
}