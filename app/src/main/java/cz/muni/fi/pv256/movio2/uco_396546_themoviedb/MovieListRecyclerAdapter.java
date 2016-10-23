package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Huvart on 17/10/16.
 */

public class MovieListRecyclerAdapter extends RecyclerView.Adapter<MovieListRecyclerAdapter.ViewHolder> {


    private Context mContext;
    private List<Movie> mData;
    ViewHolder.OnMovieSelectListener mListener;

    public MovieListRecyclerAdapter(ViewHolder.OnMovieSelectListener listener, Context context, List<Movie> data) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_movie, parent, false);
        Log.i("onCreateViewHolder:", view.toString());
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


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {
        public ImageView mCoverIv;
        private Movie mMovie;
        private Context mContext;
        OnMovieSelectListener mListener;

        public ViewHolder(View view) {
            super(view);
            mCoverIv = (ImageView) view.findViewById(R.id.list_item_icon);
        }

        public void bindView(Context context, Movie movie, OnMovieSelectListener listener) {
            if (movie == null)  return;
            mMovie = movie;
            mContext = context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mCoverIv.setImageDrawable(context.getDrawable(movie.mCoverId));
            } else {
                mCoverIv.setImageDrawable(context.getResources().getDrawable(movie.mCoverId, context.getTheme()));
            }
            mCoverIv.setScaleType(ImageView.ScaleType.FIT_XY);
            mCoverIv.setOnClickListener(this);
            mCoverIv.setOnLongClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            // set on click listener
            Log.i("clicked:",mMovie.getTitle());
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
