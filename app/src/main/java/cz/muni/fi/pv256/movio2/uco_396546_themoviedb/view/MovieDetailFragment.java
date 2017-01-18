package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.AppData;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.database.MovieManager;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.presenter.MovieDetailFragmentPresenter;


/**
 * Created by Huvart on 10/10/16.
 */

public class MovieDetailFragment extends Fragment {

    public static final String TAG = MovieDetailFragment.class.getSimpleName();
    private static final String ARGS_MOVIE = "args_movie";

    private Movie mMovie;
    private Context mContext;
    private boolean mFavourite;

    private FloatingActionButton mSaveToDatabaseActionButton;

    private MovieDetailFragmentPresenter mMovieDetailFragmentPresenter;

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
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
        mMovieDetailFragmentPresenter = new MovieDetailFragmentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        TextView titleTv = (TextView) view.findViewById(R.id.detail_movie_title);
        TextView titleLowTv = (TextView) view.findViewById(R.id.detail_movie_overview_text);
        TextView releaseDateTextView = (TextView) view.findViewById(R.id.detail_movie_release_date);
        ImageView coverIv = (ImageView) view.findViewById(R.id.detail_icon);
        ImageView backgroundIv = (ImageView) view.findViewById(R.id.background_picture);
        mSaveToDatabaseActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        if (mMovie != null) {
            titleTv.setText(mMovie.getTitle());
            titleLowTv.setText(mMovie.getOverview());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            releaseDateTextView.setText(simpleDateFormat.format(new Date( mMovie.getRelease_date())));

            mMovieDetailFragmentPresenter.checkAndSetIfMovieInDatabase(mMovie);

            mSaveToDatabaseActionButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View onClickView){
                mMovieDetailFragmentPresenter.floatingButtonOnClickListenerAction(mMovie);
                }
            });

            Picasso.with(mContext).load(AppData.base_picture_url + mMovie.getPoster_path()).resize(300, 450).into(coverIv);

            Picasso.with(mContext).load(AppData.base_picture_url + mMovie.getBackdrop_path()).into(backgroundIv);
        }


        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mSaveToDatabaseActionButton = null;
    }

    public void setFloatingButtonFavourite(boolean favourite){
        if(favourite){
            mSaveToDatabaseActionButton.setImageResource(android.R.drawable.btn_star_big_on);
        }else {
            mSaveToDatabaseActionButton.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}
