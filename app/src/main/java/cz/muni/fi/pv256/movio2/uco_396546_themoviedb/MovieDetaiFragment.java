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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


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

        TextView titleTv = (TextView) view.findViewById(R.id.detail_movie);
        TextView titleLowTv = (TextView) view.findViewById(R.id.detail_movie_low);
        ImageView coverIv = (ImageView) view.findViewById(R.id.detail_icon);
        ImageView backgroundIv = (ImageView) view.findViewById(R.id.background_picture);
        final Button saveToDatabaseButton = (Button) view.findViewById(R.id.button);

        if (mMovie != null) {
            titleTv.setText(mMovie.getTitle());
            titleLowTv.setText(mMovie.getOverview());
            final MovieManager movieManager = new MovieManager(mContext);
            if(movieManager.movieExist(mMovie)){
                mFavourite = true;
                saveToDatabaseButton.setText("-");
            }else{
                mFavourite = false;
                saveToDatabaseButton.setText("+");
            }
            saveToDatabaseButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View onClickView){
                    if(mFavourite){
                        movieManager.deleteMovie(mMovie);
                        mFavourite = false;
                        saveToDatabaseButton.setText("+");
                    }else{
                        movieManager.createMovie(mMovie);
                        mFavourite = true;
                        saveToDatabaseButton.setText("-");
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
