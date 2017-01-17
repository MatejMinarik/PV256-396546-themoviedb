package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.AppData;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.BuildConfig;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;

/**
 * Created by Huvart on 12/01/2017.
 */

public class MovieListDiscoverRecyclerAdapter extends RecyclerView.Adapter<MovieListDiscoverRecyclerAdapter.ViewHolder> {


    private MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;
    private Context mContext;
    private List<Movie> mMoviesList;
    private LayoutInflater mInflater;

    public MovieListDiscoverRecyclerAdapter(LayoutInflater inflater, MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener listener, Context context, List<Movie> movies) {
        mMoviesList = movies;
        mContext = context;
        mListener = listener;
        mInflater = inflater;
    }

    public List<Movie> getMoviesList() {
        return mMoviesList;
    }

    public void setMoviesList(List<Movie> movies) {
        mMoviesList = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.list_item_saved_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListDiscoverRecyclerAdapter.ViewHolder holder, int position) {
        Movie item = mMoviesList.get(position);
        holder.bindView(mContext, item, mListener);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        public ImageView mCoverIv;
        public TextView mTextView;
        private Movie mMovie;
        private Context mContext;
        private MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;

        public ViewHolder(View view) {
            super(view);
            mCoverIv = (ImageView) view.findViewById(R.id.list_item_icon);
            mTextView = (TextView) view.findViewById(R.id.list_item_name);
        }

        public void bindView(Context context, Movie movie, MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener listener) {
            if (movie == null)  return;
            mMovie = movie;
            mContext = context;
            if(BuildConfig.LOGING) {
                Log.d("drowing picture", mContext.toString());
                Log.d("drowing picture", mCoverIv.toString());
            }
            Picasso.with(mContext).load(AppData.base_picture_url + movie.getPoster_path()).placeholder(R.drawable.sandclock_318_10212).error(R.drawable.no_image_available).into(mCoverIv);
            //Log.d("drowing picture", movie.getPoster_path());

            mTextView.setText(movie.getTitle());
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mCoverIv.setImageDrawable(context.getDrawable(movie.mCoverId));
            } else {
                mCoverIv.setImageDrawable(context.getResources().getDrawable(movie.mCoverId, context.getTheme()));
            }*/
            mCoverIv.setScaleType(ImageView.ScaleType.FIT_XY);
            mCoverIv.setOnClickListener(this);
            mCoverIv.setOnLongClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            // set on click listener
            if(BuildConfig.LOGING) {
                Log.i("clicked:", mMovie.getTitle());
            }
            mListener.onMovieSelect(mMovie);
        }

        @Override
        public boolean onLongClick(View v) {
            if(mContext != null && mMovie != null) {
                Toast.makeText(mContext, mMovie.getTitle(), Toast.LENGTH_SHORT)
                        .show();
            }
            return true;
        }

        public interface OnMovieSelectListener {
            void onMovieSelect(Movie movie);
        }
    }
}
