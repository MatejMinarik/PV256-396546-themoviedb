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
 * Created by Huvart on 17/10/16.
 */

public class MovieListRecyclerAdapter extends RecyclerView.Adapter<MovieListRecyclerAdapter.ViewHolder> {


    private Context mContext;
    private List<Movie> mData;
    private ViewHolder.OnMovieSelectListener mListener;

    public MovieListRecyclerAdapter(ViewHolder.OnMovieSelectListener listener, Context context, List<Movie> data) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    public List<Movie> getData() {
        return mData;
    }

    public void setData(List<Movie> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_movie, parent, false);
        if(BuildConfig.LOGING) {
            Log.i("onCreateViewHolder:", view.toString());
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie item = mData.get(position);
        holder.bindView(mContext, item, mListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        public ImageView mCoverIv;
        public TextView mTextView;
        private Movie mMovie;
        private Context mContext;
        private OnMovieSelectListener mListener;

        public ViewHolder(View view) {
            super(view);
            mCoverIv = (ImageView) view.findViewById(R.id.list_item_icon);
            mTextView = (TextView) view.findViewById(R.id.list_item_name);
        }

        public void bindView(Context context, Movie movie, OnMovieSelectListener listener) {
            if (movie == null)  return;
            mMovie = movie;
            mContext = context;

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
