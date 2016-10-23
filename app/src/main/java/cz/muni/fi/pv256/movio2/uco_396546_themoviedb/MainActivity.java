package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_detail_fragment) != null) {

            mTwoPane = true;
            Log.i("is multidisplay", "is multidisplay");

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_fragment, new MovieDetaiFragment(), MovieDetaiFragment.TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            Log.i("is not multidisplay", "is not multidisplay");
            getSupportActionBar().setElevation(0f);
        }


    }

    @Override
    public void onMovieSelect(Movie movie) {
        if (mTwoPane) {
            FragmentManager fm = getSupportFragmentManager();

            MovieDetaiFragment fragment = MovieDetaiFragment.newInstance(movie);
            fm.beginTransaction()
                    .replace(R.id.movie_detail_fragment, fragment, MovieDetaiFragment.TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            startActivity(intent);
        }
    }

}
