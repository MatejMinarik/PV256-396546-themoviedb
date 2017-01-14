package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieManager;


/**
 * Created by Huvart on 10/10/16.
 */

public class MovieDetaiFragment extends Fragment {

    public static final String TAG = MovieDetaiFragment.class.getSimpleName();
    private static final String ARGS_MOVIE = "args_movie";

    private Movie mMovie;
    private Context mContext;
    private boolean mFavourite;

    public static MovieDetaiFragment newInstance(Movie movie) {
        MovieDetaiFragment fragment = new MovieDetaiFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        Bundle args = getArguments();
        if (args != null) {
            mMovie = args.getParcelable(ARGS_MOVIE);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("in creating ====", inflater.toString());
        if(container != null) {
            Log.i("in creating ====", container.toString());
        }else{
            Log.i("in creating ====", "container null");
        }
        if(savedInstanceState != null) {
            Log.i("in creating ====", savedInstanceState.toString());
        }else{
            Log.i("in creating ====", "savedInstanceState null");
        }
        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        TextView titleTv = (TextView) view.findViewById(R.id.detail_movie_title);
        TextView titleLowTv = (TextView) view.findViewById(R.id.detail_movie_overview_text);
        TextView releaseDateTextView = (TextView) view.findViewById(R.id.detail_movie_release_date);
        ImageView coverIv = (ImageView) view.findViewById(R.id.detail_icon);
        ImageView backgroundIv = (ImageView) view.findViewById(R.id.background_picture);
        final FloatingActionButton saveToDatabaseActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        if (mMovie != null) {
            titleTv.setText(mMovie.getTitle());
            titleLowTv.setText(mMovie.getOverview());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            releaseDateTextView.setText(simpleDateFormat.format(new Date( mMovie.getRelease_date())));
            final MovieManager movieManager = new MovieManager(mContext);
            if(movieManager.movieExist(mMovie)){
                mFavourite = true;
                saveToDatabaseActionButton.setImageResource(android.R.drawable.btn_star_big_on);
            }else{
                mFavourite = false;
                saveToDatabaseActionButton.setImageResource(android.R.drawable.btn_star_big_off);
            }
            saveToDatabaseActionButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View onClickView){
                    if(mFavourite){
                        movieManager.deleteMovie(mMovie);
                        mFavourite = false;
                        saveToDatabaseActionButton.setImageResource(android.R.drawable.btn_star_big_off);
                    }else{
                        movieManager.createMovie(mMovie);
                        mFavourite = true;
                        saveToDatabaseActionButton.setImageResource(android.R.drawable.btn_star_big_on);
                    }
                    Log.d("======================", mMovie.getTitle());
                }
            });

            Picasso.with(mContext).load(AppData.base_picture_url + mMovie.getPoster_path()).resize(300, 450).into(coverIv);

            Picasso.with(mContext).load(AppData.base_picture_url + mMovie.getBackdrop_path()).into(backgroundIv);
        }


        return view;
    }

}
