package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMovieSelectListener {

        //if (findViewById(R.id.article_fragment) != null) {



    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_detail_fragment) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            Log.i("is multydisplay", "is multydisplay");
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_fragment, new MovieDetaiFragment(), MovieDetaiFragment.TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            Log.i("is not multydisplay", "is not multydisplay");
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
