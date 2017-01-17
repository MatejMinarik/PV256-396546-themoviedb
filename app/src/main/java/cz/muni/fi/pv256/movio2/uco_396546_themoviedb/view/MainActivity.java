package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters.DrawerListAdapter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters.MovieListRecyclerAdapter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.interfaces.MainActivityPresenterInterface;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter.MainActivityPresenter;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainDiscoveredFragment;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MainFragment;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MovieDetailActivity;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view.MovieDetailFragment;

public class MainActivity extends AppCompatActivity implements MainActivityPresenterInterface.RequiredMainActivityOps, MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener {

    MainFragment mMainFragment;
    MainDiscoveredFragment mDiscoveredFragment;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;

    MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityPresenter = new MainActivityPresenter(this);


        setContentView(R.layout.activity_main);
        mMainFragment = new MainFragment();
        mDiscoveredFragment = new MainDiscoveredFragment();

        mMainActivityPresenter.setMainActivityPresenter(savedInstanceState);
        setDrawerLayout();
    }

    private void setDrawerLayout(){

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mMainActivityPresenter.getHamburgerMenuItems());
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMainActivityPresenter.selectItemFromDrawer(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                try {
                    invalidateOptionsMenu();
                }catch (NullPointerException e){
                    Log.e("onDrawerOpened", e.toString());
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                try {
                    invalidateOptionsMenu();
                }catch (NullPointerException e){
                    Log.e("onDrawerClosed", e.toString());
                }
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle.syncState();
    }

    // Called when invalidateOptionsMenu() is invoked
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If the nav drawer is open, hide action items related to the content view
        return mDrawerLayout.isDrawerOpen(mDrawerList);
    }

    public void mainFragmentUpdateViewApropriatly(){
        mMainFragment.updateView();
    }

    public void discoveredFragmentUpdateViewApropriatly(){
        mDiscoveredFragment.updateView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setDetailMovieFragmentDefultOnSide(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_fragment, new MovieDetailFragment(), MovieDetailFragment.TAG)
                .commit();
    }

    public void setDetailMovieFragmentOnSide(Movie movie){
        MovieDetailFragment fragment = MovieDetailFragment.newInstance(movie);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_fragment, fragment, MovieDetailFragment.TAG)
                .commit();
    }

    public void setDetailMovieFragmetOnTop(Movie movie){
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    public boolean isTwoPane(){
        return findViewById(R.id.movie_detail_fragment) != null;
    }

    public void setDiscoveredFragmentAsMain(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_list_fragment, mDiscoveredFragment, mDiscoveredFragment.TAG)
                .commit();
    }

    public void setMainFragmentAsMain(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_list_fragment, mMainFragment, mMainFragment.TAG)
                .commit();
    }

    @Override
    public void onMovieSelect(Movie movie) {
        mMainActivityPresenter.onMovieSelect(movie);
    }
}
