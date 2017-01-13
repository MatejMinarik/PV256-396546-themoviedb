package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener {

    private boolean mTwoPane;
    private boolean mDiscover = true;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<HamburgerMenuItem> mHamburgerMenuItems = new ArrayList<HamburgerMenuItem>();

    MainFragment mMainFragment;
    MainDiscoverFragment mDiscoverFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainFragment = new MainFragment();
        mDiscoverFragment = new MainDiscoverFragment();


        if (savedInstanceState == null) {
            if(mDiscover) {
                Log.d("start", "this layout");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_list_fragment, mMainFragment, mMainFragment.TAG)
                        .commit();
            }else{

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_list_fragment, mDiscoverFragment, mDiscoverFragment.TAG)
                        .commit();
            }
        }

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


         //View view = findViewById(R.id.drawerLayout)
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mHamburgerMenuItems);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });


        mHamburgerMenuItems.add(new HamburgerMenuItem("Discover"));
        mHamburgerMenuItems.add(new HamburgerMenuItem("Favourite"));
        //mHamburgerMenuItems.add(new HamburgerMenuItem("Reload"));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
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
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return drawerOpen;
    }

    private void selectItemFromDrawer(int position) {
        switch (position){
            case 0:
                if(!mDiscover){
                    mDiscover = true;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_list_fragment, mMainFragment, MainFragment.TAG)
                            .commit();
                }
                break;
            case 1:
                if(mDiscover){
                    mDiscover = false;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_list_fragment, mDiscoverFragment, MainDiscoverFragment.TAG)
                            .commit();
                }
                break;
            default:
                Log.e("selecting from drawer", position + " is undefined");
                break;
        }
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
