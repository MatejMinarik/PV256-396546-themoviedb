package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Huvart on 10/10/16.
 */

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            FragmentManager fm = getSupportFragmentManager();
            MovieDetaiFragment fragment = (MovieDetaiFragment) fm.findFragmentById(R.id.movie_detail_fragment);

            if (fragment == null) {
                fragment = MovieDetaiFragment.newInstance(movie);
                fm.beginTransaction()
                        .add(R.id.movie_detail_fragment, fragment)
                        .commit();
            }
        }
    }
}
