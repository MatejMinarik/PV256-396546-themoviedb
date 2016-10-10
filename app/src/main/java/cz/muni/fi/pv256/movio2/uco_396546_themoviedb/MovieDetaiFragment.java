package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Huvart on 10/10/16.
 */

public class MovieDetaiFragment extends Fragment {

    public static final String TAG = MovieDetaiFragment.class.getSimpleName();
    private static final String ARGS_MOVIE = "args_movie";

    private Movie mMovie;
    private Context mContext;

    public static MovieDetaiFragment newInstance(Movie movie) {
        MovieDetaiFragment fragment = new MovieDetaiFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle args = getArguments();
        if (args != null) {
            mMovie = args.getParcelable(ARGS_MOVIE);
        }
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

        TextView titleTv = (TextView) view.findViewById(R.id.detail_movie);
        TextView titleLowTv = (TextView) view.findViewById(R.id.detail_movie_low);
        ImageView coverIv = (ImageView) view.findViewById(R.id.detail_icon);

        if (mMovie != null) {
            titleTv.setText(mMovie.getTitle());
            titleLowTv.setText(mMovie.getBackdrop());
            setCoverImage(coverIv, mMovie);
        }

        return view;
    }

    private void setCoverImage(ImageView coverIv, Movie movie) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            coverIv.setImageDrawable(mContext.getDrawable(movie.mCoverId));
        } else {
            coverIv.setImageDrawable(mContext.getResources().getDrawable(movie.mCoverId, mContext.getTheme()));
        }
    }
}
