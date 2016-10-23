package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huvart on 23/10/2016.
 */

public class GenresListRecyclerAdapter extends RecyclerView.Adapter<GenresListRecyclerAdapter.ViewHolder> {

    MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;
    Context mContext;
    ArrayList<Genre> mGenreList;

    public GenresListRecyclerAdapter(MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener listener, Context context, ArrayList<Genre> genreList) {
        mGenreList = genreList;
        mContext = context;
        mListener = listener;
    }

    @Override
    public GenresListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_genre, parent, false);
        Log.i("onCreateGenreViewHolder:", view.toString());
        return new GenresListRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenresListRecyclerAdapter.ViewHolder holder, int position) {
        Genre item = mGenreList.get(position);
        holder.bindView(mContext, item, mListener);
    }

    @Override
    public int getItemCount() {
        return mGenreList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Genre mGenre;
        private RecyclerView mMovieView;
        private TextView mNameView;
        private View mView;
        private MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;
        private Context mContext;


        public ViewHolder(View itemView) {
            super(itemView);
            mMovieView = (RecyclerView) itemView.findViewById(R.id.recyclerView_genres);
            mNameView = (TextView) itemView.findViewById(R.id.genres_name);
        }


        public void bindView(Context context, Genre item, MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener listener) {
            mListener = listener;
            mContext = context;
            mNameView.setText(item.getGanreName());

            mGenre = item;
            List<Movie> mMovieList = mGenre.getMovieList();

            mMovieView.setHasFixedSize(true);

            mMovieView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            mMovieView.setItemAnimator(new DefaultItemAnimator());

            if (mGenre.getMovieList() != null && !mGenre.getMovieList().isEmpty()){
                setAdapter(mMovieView, mGenre.getMovieList());
            }
        }

        private void setAdapter(RecyclerView movieView, List<Movie> movieList) {
            MovieListRecyclerAdapter adapter = new MovieListRecyclerAdapter(mListener, mContext, movieList);
            movieView.setAdapter(adapter);
        }
    }
}
